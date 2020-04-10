package com.ulan.app.munduz.ui.basket


import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
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
import com.ulan.app.munduz.adapter.BasketAdapter
import com.ulan.app.munduz.data.room.entities.PurchaseEntity
import com.ulan.app.munduz.data.room.repository.PurchasesRepositoryImpl
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.listeners.OnChangeCountListener
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.listeners.PurchaseClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.buy.BuyFragment
import com.ulan.app.munduz.ui.details.DetailsActivity
import com.ulan.app.munduz.ui.home.HomeFragment
import kotlinx.android.synthetic.main.basket_layout.*
import kotlinx.android.synthetic.main.favorite_layout.empty_liked_products
import javax.inject.Inject

class BasketFragment : BaseFragment(), BasketView, PurchaseClickListener, OnChangeCountListener {

    @Inject
    lateinit var mPresenter: BasketPresenter

    @Inject
    lateinit var mAdapter: BasketAdapter

    @Inject
    lateinit var mPurchasesRepository: PurchasesRepositoryImpl

    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.basket_layout, container, false)
        mRecyclerView = view.findViewById(R.id.basket_recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.setToolbar()
        mPresenter.loadProducts()
        mPresenter.countSumOfPurchases()

        purchase_all.setOnClickListener {
            mPresenter.purchaseAllButtonClicked()
        }
    }

    override fun showToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.navigationIcon = null
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = resources.getString(R.string.basket)
        textToolbar.typeface = Typeface.DEFAULT
        textToolbar.textSize = resources.getDimension(R.dimen.toolbar_title_size)
    }

    override fun showEmptyData() {
        empty_liked_products.visibility = View.VISIBLE
    }

    override fun showAllProducts(purchases : ArrayList<PurchaseEntity>) {
        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        mAdapter.setProducts(purchases)
        mAdapter.setRepository(mPurchasesRepository)
        mAdapter.setListener(this)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun purchaseAll(purchases : ArrayList<PurchaseEntity>, price: Int) {
        val fragment = BuyFragment.newInstance(purchases, price)
        fragment.show(activity!!.supportFragmentManager, "buy_dialog")
    }

    override fun showSumOfPurchases(sum: Int) {
        val rub = Html.fromHtml(" &#x20bd")
        sum_of_purchase.text = "Сумма товара " + sum.toString() + rub
    }

    override fun changeSumOfPurchases(sum: Int) {

    }

    override fun decrementProduct(price: Int) {
        mPresenter.decrementCount(price)
    }

    override fun incrementProduct(price: Int) {
        mPresenter.incrementProduct(price)
    }

    override fun hidePurchaseButton() {
        sum_of_purchase.visibility = View.GONE
        purchase_all.visibility = View.GONE
    }

    override fun showPurchaseButton() {
        sum_of_purchase.visibility = View.VISIBLE
        purchase_all.visibility = View.VISIBLE
    }

    override fun onItemClick(product: Product?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.PRODUCT_ARG, product)
        startActivity(intent)
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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    companion object {
        fun newInstance(): BasketFragment {
            val args = Bundle()
            val fragment = BasketFragment()
            fragment.arguments = args
            return fragment
        }
    }

}