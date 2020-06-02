package ulanapp.munduz.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.home_layout.*
import ulanapp.munduz.R
import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.models.SliderImage
import ulanapp.munduz.data.room.repository.FavoritesRepository
import ulanapp.munduz.data.room.repository.PurchasesRepository
import ulanapp.munduz.helpers.Constants.Companion.BASKET_TURN_ON
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_ARG
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_TURN_OFF_ADD_BASKET
import ulanapp.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import ulanapp.munduz.helpers.isNetworkAvailable
import ulanapp.munduz.interfaces.OnItemClickListener
import ulanapp.munduz.ui.activities.details.DetailsActivity
import ulanapp.munduz.ui.activities.main.MainActivity
import ulanapp.munduz.ui.adapter.ProductsAdapter
import ulanapp.munduz.ui.adapter.SliderAdapter
import ulanapp.munduz.ui.base.BaseFragment
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView, OnItemClickListener {

    @Inject
    lateinit var presenter: HomePresenterImpl

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

        activity?.let {
            it.findViewById<LinearLayout>(R.id.search_layout).visibility = View.VISIBLE
            it.findViewById<EditText>(R.id.button_click).isEnabled = true
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbarTitle(true, resources.getString(R.string.app_name))
        presenter.bindView(this)
        presenter.apply {
            loadSliderImages()
            loadProducts()
        }
        handler = Handler()
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
        intent.putExtra(EXTRA_TURN_OFF_ADD_BASKET, BASKET_TURN_ON)
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
        presenter.unbindView(this)
    }

    override fun onBackPressed(): Boolean {
        activity?.let {
            it.moveTaskToBack(true)
            it.finish()
        }
        return true
    }

}
