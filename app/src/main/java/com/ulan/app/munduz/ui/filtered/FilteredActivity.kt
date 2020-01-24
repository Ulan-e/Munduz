package com.ulan.app.munduz.ui.filtered

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.ProductAdapter
import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.data.repository.RepositoryImpl
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.CATEGORY_ARG
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseActivity
import com.ulan.app.munduz.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.filtered_layout.*

class FilteredActivity : BaseActivity(), FilteredView,  OnItemClickListener{

    private lateinit var mPresenter: FilteredPresenter
    private lateinit var mRepository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filtered_layout)

        val category = intent.getStringExtra(CATEGORY_ARG)
        mRepository = RepositoryImpl(this)
        mPresenter = FilteredPresenterImpl(this, mRepository)
        mPresenter.loadProducts(category)
        showToolbar(category)
    }

    private fun showToolbar(title: String){
        setSupportActionBar(filtered_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        filtered_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        filtered_toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar_text.text = title
    }

    override fun showProgress() {
        filter_progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        filter_progress_bar.visibility = View.GONE
    }

    override fun showProducts(products: MutableList<Product>) {
        val layoutManager = GridLayoutManager(this, 2)
        filter_recycler_view.layoutManager = layoutManager
        val adapter = ProductAdapter(this, products, this)
        filter_recycler_view.adapter = adapter
        filter_recycler_view
    }

    override fun showNoProducts() {
        TODO()
    }

    override fun onItemClick(product: Product?) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.PRODUCT_ARG, product)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetachView()
    }
}