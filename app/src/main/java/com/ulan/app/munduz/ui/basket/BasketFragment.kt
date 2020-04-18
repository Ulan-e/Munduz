package com.ulan.app.munduz.ui.basket


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
import com.ulan.app.munduz.adapter.BasketAdapter
import com.ulan.app.munduz.data.models.Picture
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import com.ulan.app.munduz.helpers.RUBLE
import com.ulan.app.munduz.listeners.OnChangeSumListener
import com.ulan.app.munduz.listeners.OnItemBasketClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.buy.BuyActivity
import com.ulan.app.munduz.ui.details.DetailsActivity
import com.ulan.app.munduz.ui.home.HomeFragment
import kotlinx.android.synthetic.main.basket_layout.*
import javax.inject.Inject

class BasketFragment : BaseFragment(), BasketView, OnItemBasketClickListener, OnChangeSumListener {

    @Inject
    lateinit var mPresenter: BasketPresenter

    @Inject
    lateinit var mAdapter: BasketAdapter

    @Inject
    lateinit var mPurchasesRepository: PurchasesRepository

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

        purchase_all.setOnClickListener {
            mPresenter.purchaseButtonClicked()
        }

        open_catalog.setOnClickListener {
            mPresenter.goToHomeButtonClicked()
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
        empty_basket.visibility = View.VISIBLE
    }

    override fun showProducts(purchases: MutableList<PurchaseEntity>) {
        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        mAdapter.setProducts(purchases)
        mAdapter.setRepository(mPurchasesRepository)
        mAdapter.setListener(this)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun purchaseAll(purchases: MutableList<PurchaseEntity>, amount: Int) {
        val intent = Intent(activity!!, BuyActivity::class.java)
        intent.putExtra(Constants.EXTRA_PRODUCT_AMOUNT_ARG, amount)
        intent.putParcelableArrayListExtra(Constants.EXTRA_PURCHASES_BUY_ARG, ArrayList(purchases))
        startActivity(intent)
    }

    override fun showPurchasesAmount(amount: Int) {
        val amountText = activity!!.resources.getString(R.string.purchases_amount)
        sum_of_purchase.text = amountText + amount.toString() + RUBLE
    }

    override fun showGoToHome() {
        goToHomePage()
    }

    override fun hidePurchaseButton() {
        sum_of_purchase.visibility = View.GONE
        purchase_all.visibility = View.GONE
    }

    override fun showPurchaseButton() {
        sum_of_purchase.visibility = View.VISIBLE
        purchase_all.visibility = View.VISIBLE
    }

    override fun onItemClick(purchase: PurchaseEntity) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.EXTRA_PRODUCT_ARG, convertPurchaseToProduct(purchase))
        intent.putExtra(Constants.EXTRA_TURN_OFF_ADD_BASKET, Constants.TURN_OFF_ARG)
        startActivity(intent)
    }

    override fun onBackPressed(): Boolean {
        goToHomePage()
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

    override fun onSumChanged() {
        mPresenter.purchasesAmountChanged()
    }

    private fun goToHomePage() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HomeFragment(), HOME_FRAGMENT)
            .addToBackStack(null)
            .commit()
        val bottomNav =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        bottomNav.selectedItemId = R.id.home
    }

    private fun convertPurchaseToProduct(purchase: PurchaseEntity): Product {
        var product = Product()
        var picture = Picture()
        product.id = purchase.id
        product.name = purchase.name
        product.desc = purchase.desc
        product.cost = purchase.price
        product.priceFor = purchase.perPrice
        picture.urlImage = purchase.picture.urlImage
        product.picture = picture
        product.category = purchase.category
        return product
    }

}