package com.ulan.app.munduz.ui.favorite

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
import com.ulan.app.munduz.adapter.FavoritesAdapter
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.details.DetailsActivity
import com.ulan.app.munduz.ui.home.HomeFragment
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
        presenter.setToolbar()
        presenter.loadProducts()
    }

    override fun showToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.navigationIcon = null
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = resources.getString(R.string.favorite)
        textToolbar.typeface = Typeface.DEFAULT
        textToolbar.textSize = resources.getDimension(R.dimen.toolbar_title_size)
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