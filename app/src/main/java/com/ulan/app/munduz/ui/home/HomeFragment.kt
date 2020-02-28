package com.ulan.app.munduz.ui.home

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.ProductAdapter
import com.ulan.app.munduz.adapter.SliderAdapter
import com.ulan.app.munduz.data.model.SliderImage
import com.ulan.app.munduz.data.roomdatabase.RoomRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_ARG
import com.ulan.app.munduz.helpers.isNetworkAvailable
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.buy.BuyFragment
import com.ulan.app.munduz.ui.details.DetailsActivity
import com.ulan.app.munduz.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.home_layout.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView, OnItemClickListener {

    @Inject
    lateinit var mPresenter: HomePresenter

    @Inject
    lateinit var mAdapter: ProductAdapter

    @Inject
    lateinit var mRoomRepository: RoomRepository

    var mSliderAdapter: SliderAdapter? = null

    private lateinit var mMainActivity: MainActivity
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mViewPager: ViewPager

    lateinit var handler: Handler
    private var page = 0
    private var delay: Long = 5000L
    private val runnable = object : Runnable {
        override fun run() {
            if(mSliderAdapter != null) {
                if (mSliderAdapter!!.count == page) {
                    page = 0
                } else {
                    page++
                }
            }
            mViewPager.setCurrentItem(page, true)
            handler.postDelayed(this, delay)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_layout, container, false)
        mRecyclerView = view.findViewById(R.id.home_recycler_view)
        mViewPager = view.findViewById(R.id.view_pager)
        mMainActivity = (activity as MainActivity)
        mMainActivity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.VISIBLE
        mMainActivity.findViewById<EditText>(R.id.button_click).isEnabled = true
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.setToolbar()
        mPresenter.loadSliderImages()
        mPresenter.loadProducts()
        handler = Handler()
    }

    override fun showToolbar() {
        val toolbar = mMainActivity.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.navigationIcon = null
        mMainActivity.supportActionBar?.hide()
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        val typeface = Typeface.createFromAsset(activity!!.assets, "fonts/forte.ttf")
        textToolbar.text = resources.getString(R.string.app_name)
        textToolbar.typeface = typeface
        textToolbar.textSize = resources.getDimension(R.dimen.toolbar_app_title_size)
    }

    override fun isNetworkOn(): Boolean {
        return isNetworkAvailable(activity!!.applicationContext)
    }

    override fun showErrorNetwork() {
        home_title.visibility = View.GONE
        mMainActivity.findViewById<Button>(R.id.button_click).isEnabled = false
        content_scroll_view.visibility = View.GONE
        error_layout.visibility = View.VISIBLE
        error_network_button.setOnClickListener {
            val fragment = activity!!.supportFragmentManager.findFragmentByTag("homef")
            val ft = activity!!.supportFragmentManager.beginTransaction()
            ft.detach(fragment!!)
            ft.attach(fragment)
            ft.commit()
        }
    }

    override fun showProducts(products: MutableList<Product>) {
        val layoutManager = GridLayoutManager(activity, 2)
        mRecyclerView.layoutManager = layoutManager
        mAdapter.setProducts(products)
        mAdapter.setRepository(mRoomRepository)
        mRecyclerView.adapter = mAdapter
    }

    override fun showEmptyData() {
        empty_new_products.visibility = View.VISIBLE
    }

    override fun showSliderImages(images: ArrayList<SliderImage>) {
        mSliderAdapter = SliderAdapter(activity!!.applicationContext, images)
        mViewPager.adapter = mSliderAdapter
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                page = position
            }

        })
    }

    override fun onItemClick(product: Product?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(PRODUCT_ARG, product)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        mAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, delay)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onBackPressed(): Boolean {
        super.onBackPressed()
        activity!!.moveTaskToBack(true)
        activity!!.finish()
        return true
    }

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
