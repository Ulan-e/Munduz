package com.ulan.app.munduz.ui.fragments.favorite

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.ui.adapter.FavoritesAdapter
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import com.ulan.app.munduz.interfaces.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.activities.details.DetailsActivity
import com.ulan.app.munduz.ui.fragments.home.HomeFragment
import kotlinx.android.synthetic.main.favorite_layout.*
import javax.inject.Inject

class FavoriteFragment : BaseFragment(), FavoriteView, OnItemClickListener {

    @Inject
    lateinit var presenter: FavoritePresenter

    @Inject
    lateinit var adapter: FavoritesAdapter

    @Inject
    lateinit var favoritesRepository: FavoritesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbarTitle(resources.getString(R.string.favorite))

        presenter.loadProducts()

    }

    override fun showEmptyData() {
        empty_favorites.visibility = View.VISIBLE
    }

    override fun showProducts(products: MutableList<Product>) {
        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        adapter.setProducts(products)
        adapter.setRepository(favoritesRepository)
        favorite_recycler_view.layoutManager = layoutManager
        favorite_recycler_view.adapter = adapter
    }

    override fun onItemClick(product: Product) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.EXTRA_PRODUCT_ARG, product)
        startActivity(intent)
    }

    override fun onBackPressed(): Boolean {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HomeFragment(), HOME_FRAGMENT)
            .addToBackStack(null)
            .commit()
        val bottomNav = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        bottomNav.selectedItemId = R.id.home
        return true
    }

    override fun onStart() {
        super.onStart()
        adapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }
}