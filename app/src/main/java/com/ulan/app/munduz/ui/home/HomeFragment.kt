package com.ulan.app.munduz.ui.home

import android.content.Intent
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
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.ProductsAdapter
import com.ulan.app.munduz.adapter.SliderAdapter
import com.ulan.app.munduz.data.models.SliderImage
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_ARG
import com.ulan.app.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import com.ulan.app.munduz.helpers.isNetworkAvailable
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.details.DetailsActivity
import com.ulan.app.munduz.ui.main.MainActivity
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.home_layout.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView, OnItemClickListener {

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var favoritesRepository: FavoritesRepository

    @Inject
    lateinit var purchasesRepository: PurchasesRepository

    private lateinit var mainActivity: MainActivity
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    private var sliderImageAdapter: SliderAdapter? = null

    lateinit var handler: Handler
    private var page = 0
    private var delay: Long = 5000L

    private val runnable = object : Runnable {
        override fun run() {
            if (sliderImageAdapter != null) {
                if (sliderImageAdapter!!.count == page) {
                    page = 0
                } else {
                    page++
                }
            }
            viewPager.setCurrentItem(page, true)
            handler.postDelayed(this, delay)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_layout, container, false)
        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_dots)
        tabLayout.setupWithViewPager(viewPager, true)
        mainActivity = (activity as MainActivity)
        mainActivity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.VISIBLE
        mainActivity.findViewById<EditText>(R.id.button_click).isEnabled = true
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setToolbar()
        presenter.loadSliderImages()
        presenter.loadProducts()
        handler = Handler()
    }

    override fun showToolbar() {
        val toolbar = mainActivity.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.navigationIcon = null
        mainActivity.supportActionBar?.hide()
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        val typeface = ResourcesCompat.getFont(activity!!, R.font.forte)
        textToolbar.typeface = typeface
        textToolbar.text = resources.getString(R.string.app_name)
        textToolbar.textSize = resources.getDimension(R.dimen.toolbar_app_title_size)
    }

    override fun isNetworkOn(): Boolean {
        return isNetworkAvailable(activity!!.applicationContext)
    }

    override fun showErrorNetwork() {
        home_title.visibility = View.GONE
        mainActivity.findViewById<Button>(R.id.button_click).isEnabled = false
        content_scroll_view.visibility = View.GONE
        error_layout.visibility = View.VISIBLE
        error_network_button.setOnClickListener {
            val fragment = activity!!.supportFragmentManager.findFragmentByTag(HOME_FRAGMENT)
            val ft = activity!!.supportFragmentManager.beginTransaction()
            ft.detach(fragment!!)
            ft.attach(fragment)
            ft.commit()
        }
    }

    override fun showProducts(products: MutableList<Product>) {
        val layoutManager = GridLayoutManager(activity, 2)
        home_recycler_view.layoutManager = layoutManager
        productsAdapter.setProducts(products)
        productsAdapter.setRepositories(favoritesRepository, purchasesRepository)
        home_recycler_view.adapter = productsAdapter
    }

    override fun showEmptyData() {
        empty_new_products.visibility = View.VISIBLE
    }

    override fun showSliderImages(images: List<SliderImage>) {
        sliderImageAdapter = SliderAdapter(activity!!.applicationContext, images)
        viewPager.adapter = sliderImageAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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

    override fun onItemClick(product: Product) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(EXTRA_PRODUCT_ARG, product)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        productsAdapter.notifyDataSetChanged()
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
        presenter.detachView()
    }

    override fun onBackPressed(): Boolean {
        super.onBackPressed()
        activity!!.moveTaskToBack(true)
        activity!!.finish()
        return true
    }

}
