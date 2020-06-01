package com.ulan.app.munduz.ui.activities.orders

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.models.Order
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_AMOUNT_ARG
import com.ulan.app.munduz.helpers.Constants.Companion.EXTRA_PURCHASES_BUY_ARG
import com.ulan.app.munduz.helpers.Constants.Companion.PURCHASE_FRAGMENT
import com.ulan.app.munduz.ui.base.BaseActivity
import com.ulan.app.munduz.ui.fragments.purchase.PurchaseFragment
import kotlinx.android.synthetic.main.orders_layout.*
import javax.inject.Inject


class OrdersActivity : BaseActivity(), OrdersView {

    @Inject
    lateinit var presenter: OrdersPresenter

    private lateinit var purchases: MutableList<PurchaseEntity>

    private lateinit var radioButtonText: String

    private var amount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders_layout)
        purchases = intent.getParcelableArrayListExtra(EXTRA_PURCHASES_BUY_ARG)
        amount = intent.getIntExtra(EXTRA_PRODUCT_AMOUNT_ARG, -1)
        radioButtonText = resources.getString(R.string.delivery)
        order_is_with_delivery.setOnCheckedChangeListener { group, checkedId ->
            if (R.id.delivery == checkedId) {
                radioButtonText = resources.getString(R.string.delivery)
                setVisibilitiesOfDelivery(View.VISIBLE)
                setVisibilitiesOfPickUp(View.GONE)
            } else {
                radioButtonText = resources.getString(R.string.pickup)
                setVisibilitiesOfDelivery(View.GONE)
                setVisibilitiesOfPickUp(View.VISIBLE)
            }
        }

        presenter.setToolbar()
        presenter.setProducts(purchases)
        presenter.setPurchasesAmount(amount)

        order_button.setOnClickListener {
            presenter.sendButtonClicked()
        }

        cancel_button.setOnClickListener {
            presenter.cancelButtonClicked()
        }

    }

    override fun showToolbar() {
        setSupportActionBar(order_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val emptySpace = "       "
        order_toolbar_text.text = resources.getString(R.string.order) + emptySpace
        order_toolbar_text.typeface = Typeface.DEFAULT
        order_toolbar_text.textSize = resources.getDimension(R.dimen.toolbar_title_size)
        order_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        order_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun humanReadableArray(purchases: MutableList<PurchaseEntity>): StringBuilder {
        var result: StringBuilder = java.lang.StringBuilder()
        for (item in purchases) {
            result.append(item.name + ", цена за " + item.perPriceInc + ", цена " + item.priceInc + "\n")
        }
        return result
    }

    override fun showTotalPurchases(total: String) {
        total_purchase.text = total
    }

    override fun getInputOrder(): Order {
        var order = Order()
        order.amountPurchases = amount
        order.purchases = humanReadableArray(purchases).toString()
        order.clientName = client_name.text.toString()
        order.clientPhoneNumber = client_phone_number.text.toString()
        if (radioButtonText == resources.getString(R.string.delivery)) {
            order.purchaseMethod =
                radioButtonText + " Метро" + client_metro.text.toString() + "\n" + client_address.text.toString()
        } else {
            order.purchaseMethod =
                radioButtonText + client_time.text.toString()
        }
        order.comment = client_comment.text.toString()
        return order
    }

    override fun isNotEmptyFieldsDelivery(): Boolean {
        if (client_name.text.toString() == "" && client_phone_number.text.toString() == "") {
            var message = resources.getString(R.string.empty_fields)
            showSnackBar(message)
            return false
        }
        return true
    }

    override fun cancelOrder() {
        finish()
    }

    override fun goToPurchaseMethod(order: Order) {
        var fragment = PurchaseFragment.newInstance(order)
        fragment.show(supportFragmentManager, PURCHASE_FRAGMENT)
    }

    private fun showSnackBar(text: String) {
        val snack = Snackbar.make(relative_layout, text, Snackbar.LENGTH_SHORT)
        snack.show()
    }

    override fun showEmptyData() {
        //TODO
    }

    private fun setVisibilitiesOfDelivery(visibility: Int) {
        client_metro_container.visibility = visibility
        client_metro.visibility = visibility
        client_address_container.visibility = visibility
        client_address.visibility = visibility
    }

    private fun setVisibilitiesOfPickUp(visibility: Int) {
        client_time_container.visibility = visibility
        client_time.visibility = visibility
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}