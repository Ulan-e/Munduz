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
import javax.inject.Inject

class LikedFragment: BaseFragment(), LikedView, OnItemClickListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTextView: TextView

    @Inject
    lateinit var mPresenter: LikedPresenter

    @Inject
    lateinit var mAdapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.liked_layout, container, false)

        mRecyclerView = view.findViewById(R.id.liked_recycler_view)
        mTextView = view.findViewById(R.id.liked_text_empty)
        mPresenter.initToolbar()
        mPresenter.loadProducts()
        return view
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
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun showNoLikedProducts() {
        mTextView.text = "Liked is Empty"
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