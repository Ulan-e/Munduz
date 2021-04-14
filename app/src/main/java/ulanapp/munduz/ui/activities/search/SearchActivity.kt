package ulanapp.munduz.ui.activities.search

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ulanapp.munduz.data.models.Product
import kotlinx.android.synthetic.main.search_layout.*
import ulanapp.munduz.R
import ulanapp.munduz.helpers.Constants
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_ARG
import ulanapp.munduz.interfaces.OnItemClickListener
import ulanapp.munduz.ui.activities.details.DetailsActivity
import ulanapp.munduz.ui.adapter.SearchAdapter
import ulanapp.munduz.ui.base.BaseActivity
import javax.inject.Inject

class SearchActivity : BaseActivity(), SearchView, OnItemClickListener,
    android.widget.SearchView.OnQueryTextListener {

    @Inject
    lateinit var presenter: SearchPresenterImpl

    @Inject
    lateinit var adapter: SearchAdapter

    private lateinit var products: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_layout)
        checkInternetConnection()

        presenter.bindView(this)
        presenter.loadProducts()

        search_view_full.setIconifiedByDefault(false)
        search_view_full.requestFocus()

        search_view_full.setOnQueryTextListener(this)
        val hintText = resources.getString(R.string.search_hint)
        search_view_full.queryHint = hintText

        press_back.setOnClickListener {
            finish()
        }
    }

    override fun showToolbar() {
        TODO()
    }

    override fun showProducts(products: MutableList<Product>) {
        this.products = products
        val layoutManager = LinearLayoutManager(this)
        search_results.layoutManager = layoutManager
        adapter.setProducts(products)
        search_results.adapter = adapter
    }

    override fun showEmptyData() {
        Toast.makeText(this, resources.getString(R.string.empty_searched_list), Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(product: Product) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_PRODUCT_ARG, product)
        intent.putExtra(Constants.EXTRA_TURN_OFF_ADD_BASKET, Constants.BASKET_TURN_ON)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        adapter.filter.filter(p0)
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        adapter.filter.filter(p0)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView(this)
    }
}