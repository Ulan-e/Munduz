package ulanapp.munduz.ui.fragments.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ulanapp.munduz.data.models.Product
import kotlinx.android.synthetic.main.favorite_layout.*
import ulanapp.munduz.R
import ulanapp.munduz.data.room.repository.FavoritesRepository
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_ARG
import ulanapp.munduz.interfaces.OnItemClickListener
import ulanapp.munduz.ui.activities.details.DetailsActivity
import ulanapp.munduz.ui.adapter.FavoritesAdapter
import ulanapp.munduz.ui.base.BaseFragment
import javax.inject.Inject

class FavoriteFragment : BaseFragment(), FavoriteView, OnItemClickListener {

    @Inject
    lateinit var presenter: FavoritePresenterImpl

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
        checkInternetConnection()
        showToolbarTitle(
            withBackButton = false,
            isAppName = false,
            title = resources.getString(R.string.favorite)
        )

        presenter.bindView(this)
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
        intent.putExtra(EXTRA_PRODUCT_ARG, product)
        startActivity(intent)
    }

    override fun onBackPressed(): Boolean {
        goToHome()
        return true
    }

    override fun onStart() {
        super.onStart()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView(this)
    }
}