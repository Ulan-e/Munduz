package com.ulan.app.munduz.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.ProductAdapter
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_ARG
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.home_layout.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView, OnItemClickListener {

    @Inject
    lateinit var mPresenter: HomePresenter

    @Inject
    lateinit var mAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_layout, container, false)
    }

    override fun showToolbar(){
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.VISIBLE
        activity.supportActionBar?.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.setToolbar()
        mPresenter.loadProducts()
    }

    override fun showProgress() {
        home_progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        home_progress_bar.visibility = View.GONE
    }

    override fun showNoProducts() {
        showNoProducts()
    }

    override fun showProducts(products: MutableList<Product>) {
        val layoutManager = GridLayoutManager(activity, 2)
        home_recycler_view.layoutManager = layoutManager
        mAdapter.setProducts(products)
        mAdapter.setItemClickListener(this)
        home_recycler_view.adapter = mAdapter
    }

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onItemClick(product: Product?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(PRODUCT_ARG, product)
        startActivity(intent)
    }
}