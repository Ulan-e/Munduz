package com.ulan.app.munduz.ui.liked

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.ProductAdapter
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.liked_layout.*
import javax.inject.Inject

class LikedFragment: BaseFragment(), LikedView, OnItemClickListener {

    @Inject
    lateinit var mPresenter: LikedPresenter

    @Inject
    lateinit var mAdapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.liked_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter.initToolbar()
        mPresenter.loadProducts()
    }

    override fun showToolbar(){
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = resources.getString(R.string.basket)
    }

    override fun showLikedProducts(products: MutableList<Product>) {
        val layoutManager  =  GridLayoutManager(activity, 2)
        mAdapter.setProducts(products)
        mAdapter.setItemClickListener(this)
        liked_recycler_view.layoutManager = layoutManager
        liked_recycler_view.adapter = mAdapter
    }

    override fun showNoLikedProducts() {
        empty_liked_products.visibility = View.VISIBLE
    }

    override fun onItemClick(product: Product?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.PRODUCT_ARG, product)
        startActivity(intent)
    }

    companion object{
        fun newInstance(): LikedFragment {
            val args = Bundle()
            val fragment = LikedFragment()
            fragment.arguments = args
            return fragment
        }
    }
}