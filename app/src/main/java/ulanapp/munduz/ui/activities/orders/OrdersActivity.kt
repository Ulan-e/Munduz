package ulanapp.munduz.ui.activities.orders

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.orders_layout.*
import ulanapp.munduz.R
import ulanapp.munduz.data.models.Order
import ulanapp.munduz.data.models.PurchaseEntity
import ulanapp.munduz.helpers.Constants.Companion.EMPTY_SPACE
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_AMOUNT_ARG
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_PURCHASES_BUY_ARG
import ulanapp.munduz.helpers.Constants.Companion.PURCHASE_FRAGMENT
import ulanapp.munduz.ui.base.BaseActivity
import ulanapp.munduz.ui.fragments.purchase.PurchaseFragment
import javax.inject.Inject


class OrdersActivity : BaseActivity(), OrdersView {

    @Inject
    lateinit var presenter: OrdersPresenterImpl

    private lateinit var purchases: MutableList<PurchaseEntity>

    private lateinit var radioButtonText: String

    private var amount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders_layout)

        presenter.bindView(this)

        purchases = intent.getParcelableArrayListExtra(EXTRA_PURCHASES_BUY_ARG)
        amount = intent.getIntExtra(EXTRA_PRODUCT_AMOUNT_ARG, -1)

        setRadioButtonText()


        presenter.apply {
            setToolbar()
            setProducts(purchases)
            setPurchasesAmount(amount)
        }


        order_button.setOnClickListener {
            presenter.sendButtonClicked()
        }

        cancel_button.setOnClickListener {
            presenter.cancelButtonClicked()
        }

    }

    private fun setRadioButtonText() {
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
    }

    override fun showToolbar() {
        setSupportActionBar(order_toolbar)
        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        order_toolbar_text.apply {
            text = resources.getString(R.string.order) + EMPTY_SPACE
            typeface = Typeface.DEFAULT
            textSize = resources.getDimension(R.dimen.toolbar_title_size)
        }

        order_toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun humanReadableArray(purchases: MutableList<PurchaseEntity>): StringBuilder {
        var result: StringBuilder = java.lang.StringBuilder()
        for (item in purchases) {
            result.append(
                item.name + ", цена за " +
                        item.perPriceInc + ", цена " +
                        item.priceInc + "\n"
            )
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

    override fun isNotEmptyFields(): Boolean {
        if (client_name.text.toString() == "" && client_phone_number.text.toString() == "") {
            val message = resources.getString(R.string.empty_fields)
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
        presenter.unbindView(this)
    }

}