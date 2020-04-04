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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.FavoriteProductAdapter
import com.ulan.app.munduz.data.roomdatabase.RoomRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.listeners.OnFavoriteItemClickListener
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.buy.BuyFragment
import com.ulan.app.munduz.ui.details.DetailsActivity
import com.ulan.app.munduz.ui.home.HomeFragment
import kotlinx.android.synthetic.main.favorite_layout.*
import javax.inject.Inject

class FavoriteFragment: BaseFragment(), FavoriteView, OnFavoriteItemClickListener {

    @Inject
    lateinit var mPresenter: FavoritePresenter

    @Inject
    lateinit var mAdapter: FavoriteProductAdapter

    @Inject
    lateinit var mRoomRepository: RoomRepository

    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.favorite_layout, container, false)
        mRecyclerView = view.findViewById(R.id.liked_recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.setToolbar()
        mPresenter.loadProducts()
    }

    override fun showToolbar(){
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
        empty_liked_products.visibility = View.VISIBLE
    }

    override fun showLikedProducts(products: MutableList<Product>) {
        val layoutManager  =  LinearLayoutManager(activity!!.applicationContext)
        mAdapter.setProducts(products)
        mAdapter.setRepository(mRoomRepository)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun onItemClick(product: Product?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.PRODUCT_ARG, product)
        startActivity(intent)
    }

    override fun onBuyClick(product: Product?) {
        val buyFragment = BuyFragment.newInstance(product!!)
        buyFragment.show(activity!!.supportFragmentManager, "buy_dialog")
    }

    override fun onBackPressed(): Boolean {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HomeFragment(), "homef")
            .addToBackStack(null)
            .commit()
        val bottomNav = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        bottomNav.selectedItemId = R.id.home
        return true
    }

    override fun onStart() {
        super.onStart()
        mAdapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.detachView()
    }

    companion object{
        fun newInstance(): FavoriteFragment {
            val args = Bundle()
            val fragment = FavoriteFragment()
            fragment.arguments = args
            return fragment
        }
    }

}