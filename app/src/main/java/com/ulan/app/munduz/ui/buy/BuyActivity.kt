package com.ulan.app.munduz.ui.buy

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.models.Order
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_AMOUNT_ARG
import com.ulan.app.munduz.helpers.Constants.Companion.EXTRA_PURCHASES_BUY_ARG
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.base.BaseActivity
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.basket.BasketFragment
import com.ulan.app.munduz.ui.home.HomeFragment
import com.ulan.app.munduz.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.buy_layout.*
import kotlinx.android.synthetic.main.details_layout.*
import javax.inject.Inject
import javax.inject.Provider


class BuyActivity : BaseActivity(), BuyView {

    @Inject
    lateinit var mPresenter: BuyPresenter

    @Inject
    lateinit var mSendEmailHelper: Provider<SendEmailHelper>

    private lateinit var mPurchases: MutableList<PurchaseEntity>
    private var mAmount: Int = 0
    private lateinit var mRadioButtonText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_layout)
        mPurchases = intent.getParcelableArrayListExtra(EXTRA_PURCHASES_BUY_ARG)
        mAmount = intent.getIntExtra(EXTRA_PRODUCT_AMOUNT_ARG, -1)
        mRadioButtonText = resources.getString(R.string.delivery)
        order_is_with_delivery.setOnCheckedChangeListener { group, checkedId ->
            if (R.id.delivery == checkedId) {
                mRadioButtonText = resources.getString(R.string.delivery)
                setVisibilitiesOfDelivery(View.VISIBLE)
                setVisibilitiesOfPickUp(View.GONE)
            } else {
                mRadioButtonText = resources.getString(R.string.pickup)
                setVisibilitiesOfDelivery(View.GONE)
                setVisibilitiesOfPickUp(View.VISIBLE)
            }
        }

        mPresenter.setToolbar()
        mPresenter.setProducts(mPurchases)
        mPresenter.setPurchasesAmount(mAmount)
        val sendEmailHelper = mSendEmailHelper.get()
        mPresenter.setSendEmailHelper(sendEmailHelper)

        order_button.setOnClickListener {
            mPresenter.sendButtonClicked()
        }
        cancel_button.setOnClickListener {
            mPresenter.cancelButtonClicked()
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
            result.append("Товар " + item.name + ", цена за " + item.perPriceInc + ", цена " + item.priceInc + "\n")
        }
        return result
    }

    override fun showTotalPurchases(total: String) {
        total_purchase.text = total
    }

    override fun getInputOrder(): Order {
        var order = Order()
        order.amountPurchases = mAmount
        order.purchases = humanReadableArray(mPurchases).toString()
        order.clientName = client_name.text.toString()
        order.clientPhoneNumber = client_phone_number.text.toString()
        order.clientPhoneNumberSecond = client_phone_number_second.text.toString()
        if (mRadioButtonText == resources.getString(R.string.delivery)) {
            order.purchaseMethod =
                mRadioButtonText + "\n" + client_metro.text.toString() + "\n" + client_address.text.toString()
        }else{
            order.purchaseMethod =
                mRadioButtonText + "\n" + client_time.text.toString()
        }
        order.comment = client_comment.text.toString()
        return order
    }

    override fun isNotEmptyFields(): Boolean {
        if (client_name.text.toString() == "" || client_phone_number.text.toString() == "") {
            var message = resources.getString(R.string.empty_fields)
            showSnackBar(message)
            return false
        }
        return true
    }

    override fun successOrder() {
        var successMessage = resources.getString(R.string.success_order)
        showSnackBar(successMessage)
        Handler().postDelayed({
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, BasketFragment.newInstance(), Constants.BASKET_FRAGMENT)
                .addToBackStack(null)
                .commit()
        }, 3000)
    }

    override fun cancelOrder() {
       finish()
    }

    override fun showEmptyData() {

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

    private fun showSnackBar(text: String) {
        val snack = Snackbar.make(relative_layout, text, Snackbar.LENGTH_SHORT)
        snack.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}