package com.ulan.app.munduz.ui.search

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.SearchAdapter
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseActivity
import com.ulan.app.munduz.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.search_layout.*
import javax.inject.Inject

class SearchActivity: BaseActivity(), SearchView, OnItemClickListener,
    android.widget.SearchView.OnQueryTextListener {

    @Inject
    lateinit var mPresenter: SearchPresenter

    @Inject
    lateinit var mAdapter: SearchAdapter

    private lateinit var mProducts: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_layout)

        search_view_full.setIconifiedByDefault(false)
        search_view_full.requestFocus()

        search_view_full.setOnQueryTextListener(this)
        search_view_full.queryHint="Найти товар"

        mPresenter.loadProducts()

        press_back.setOnClickListener{
            finish()
        }
    }

    override fun showToolbar() {
        TODO()
    }

    override fun showProducts(products: MutableList<Product>) {
        mProducts = products
        val layoutManager = LinearLayoutManager(this)
        search_results.layoutManager = layoutManager
        mAdapter.setProducts(products)
        search_results.adapter = mAdapter
    }

    override fun showEmptyData() {
        Toast.makeText(this, resources.getString(R.string.empty_list), Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(product: Product) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.PRODUCT_ARG, product)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        mAdapter.filter.filter(p0)
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        mAdapter.filter.filter(p0)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}