package ulanapp.munduz.ui.activities.orders

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.orders_layout.*
import ulanapp.munduz.R
import ulanapp.munduz.data.models.Order
import ulanapp.munduz.data.room.repository.PurchasesRepository
import ulanapp.munduz.helpers.Constants.Companion.EMPTY_SPACE
import ulanapp.munduz.helpers.Constants.Companion.PURCHASE_FRAGMENT
import ulanapp.munduz.helpers.RUBLE
import ulanapp.munduz.ui.base.BaseActivity
import ulanapp.munduz.ui.fragments.purchase.PurchaseFragment
import javax.inject.Inject


class OrdersActivity : BaseActivity(), OrdersView {

    @Inject
    lateinit var presenter: OrdersPresenterImpl

    @Inject
    lateinit var purchasesRepository: PurchasesRepository

    private lateinit var radioButtonText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders_layout)

        presenter.bindView(this)
        presenter.setWithDeliveryOrNot(true)

        setRadioButtonText()

        presenter.apply {
            setToolbar()
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
                presenter.setWithDeliveryOrNot(true)
            } else {
                radioButtonText = resources.getString(R.string.pickup)
                setVisibilitiesOfDelivery(View.GONE)
                setVisibilitiesOfPickUp(View.VISIBLE)
                presenter.setWithDeliveryOrNot(false)
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

    override fun showTotalPurchases(sum: Int) {
        val amountOfPurchases = "К оплате $sum $RUBLE"
        total_purchase.text = amountOfPurchases
    }

    override fun getInputOrder(): Order {
        val order = Order()
        order.amountPurchases = presenter.getAmount()
        order.purchases = presenter.getAllPurchases()
        order.clientName = client_name.text.toString()
        order.clientPhoneNumber = client_phone_number.text.toString()
        if (radioButtonText == resources.getString(R.string.delivery)) {
            order.purchaseMethod =
                radioButtonText + " " + client_metro.text.toString() + "\n" + client_address.text.toString()
        } else {
            order.purchaseMethod =
                radioButtonText + " " + client_time.text.toString()
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
        val fragment = PurchaseFragment.newInstance(order)
        fragment.show(supportFragmentManager, PURCHASE_FRAGMENT)
    }

    private fun showSnackBar(text: String) {
        val snack = Snackbar.make(coordinator_orders, text, Snackbar.LENGTH_SHORT)
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
        client_delivery_price.visibility = visibility
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