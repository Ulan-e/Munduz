package com.ulan.app.munduz.ui.fragments.basket


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.models.Picture
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.RUBLE
import com.ulan.app.munduz.interfaces.OnChangeSumListener
import com.ulan.app.munduz.interfaces.OnItemBasketClickListener
import com.ulan.app.munduz.ui.activities.details.DetailsActivity
import com.ulan.app.munduz.ui.activities.orders.OrdersActivity
import com.ulan.app.munduz.ui.adapter.BasketAdapter
import com.ulan.app.munduz.ui.base.BaseFragment
import kotlinx.android.synthetic.main.basket_layout.*
import javax.inject.Inject

class BasketFragment : BaseFragment(), BasketView, OnItemBasketClickListener, OnChangeSumListener {

    @Inject
    lateinit var presenter: BasketPresenterImpl

    @Inject
    lateinit var adapter: BasketAdapter

    @Inject
    lateinit var purchasesRepository: PurchasesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.basket_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbarTitle(false, resources.getString(R.string.basket))

        presenter.bindView(this)
        presenter.loadProducts()

        purchase_all.setOnClickListener {
            presenter.purchaseButtonClicked()
        }

        open_catalog.setOnClickListener {
            presenter.goToHomeButtonClicked()
        }
    }

    override fun showEmptyData() {
        empty_basket.visibility = View.VISIBLE
    }

    override fun showProducts(purchases: MutableList<PurchaseEntity>) {
        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        adapter.setProducts(purchases)
        adapter.setRepository(purchasesRepository)
        adapter.setListener(this)
        basket_recycler_view.layoutManager = layoutManager
        basket_recycler_view.adapter = adapter
    }

    override fun purchaseAll(purchases: MutableList<PurchaseEntity>, amount: Int) {
        val intent = Intent(activity!!, OrdersActivity::class.java)
        intent.putExtra(Constants.EXTRA_PRODUCT_AMOUNT_ARG, amount)
        intent.putParcelableArrayListExtra(Constants.EXTRA_PURCHASES_BUY_ARG, ArrayList(purchases))
        startActivity(intent)
    }

    override fun showAmountPurchases(amount: Int) {
        val amountText = activity!!.resources.getString(R.string.purchases_amount)
        sum_of_purchase.text = amountText + amount.toString() + RUBLE
    }

    override fun showGoToHome() {
        goToHome()
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
        intent.putExtra(Constants.EXTRA_TURN_OFF_ADD_BASKET, Constants.BASKET_TURN_OFF)
        startActivity(intent)
    }

    override fun onBackPressed(): Boolean {
        goToHome()
        return true
    }

    override fun onStart() {
        super.onStart()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView(this)
    }

    override fun onSumChanged() {
        presenter.purchasesAmountChanged()
    }

    private fun convertPurchaseToProduct(purchase: PurchaseEntity): Product {
        val product = Product()
        val pictures = Picture()
        product.id = purchase.id
        product.name = purchase.name
        product.desc = purchase.desc
        product.cost = purchase.price
        product.priceFor = purchase.perPrice
        pictures.urlImage = purchase.picture.urlImage
        pictures.urlImage2 = purchase.picture.urlImage2
        pictures.urlImage3 = purchase.picture.urlImage3
        product.picture = pictures
        product.category = purchase.category
        return product
    }

}