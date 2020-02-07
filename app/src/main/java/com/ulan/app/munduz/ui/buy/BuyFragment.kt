package com.ulan.app.munduz.ui.buy

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.model.Order
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_BUY_ARG
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.buy_layout.*
import kotlinx.android.synthetic.main.buy_layout.view.*
import javax.inject.Inject
import javax.inject.Provider

class BuyFragment : BaseDialogFragment(), BuyView {

    @Inject
    lateinit var mPresenter: BuyPresenter

    @Inject
    lateinit var mSendEmailHelper: Provider<SendEmailHelper>

    private lateinit var mProduct: Product
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mProduct = arguments!!.getParcelable(PRODUCT_BUY_ARG)
        mView = inflater.inflate(R.layout.buy_layout, container, false)
        return mView
    }

    override fun showToolbar() {
        title_buy_fragment.text = "Заказ"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.setToolbar()
        mPresenter.setProduct(mProduct)

        val sendEmailHelper = mSendEmailHelper.get()
        mPresenter.setSendEmailHelper(sendEmailHelper)
        order_product_name.text = mProduct.name

        order_button.setOnClickListener {
            mPresenter.sendButtonClicked()
        }

        cancel_button.setOnClickListener {
            mPresenter.cancelButtonClicked()
        }


        order_increment.setOnClickListener {
            order_count.text = incrementProduct().toString()
        }

        order_decrement.setOnClickListener {
            order_count.text = decrementProduct().toString()
        }

    }

    private fun incrementProduct(): Int {
        var count = order_count.text.toString().toInt()
        count = count.inc()
        return count
    }

    private fun decrementProduct(): Int {
        var count = order_count.text.toString().toInt()
        if (count == 1) {
            return 1
        }
        count = count.dec()
        return count
    }

    override fun getInputOrder(): Order {
        val order = Order()
        order.productName = mProduct.name
        order.productKey = mProduct.id
        val orderCount = order_count.text.toString()
        order.productCount = orderCount.toInt()
        order.withDelivery = order_with_delivery.isSelected
        order.clientName = client_name.text.toString()
        order.clientPhoneNumber = client_phone_number.text.toString()
        order.orderTime = System.currentTimeMillis()
        return order
    }

    override fun isNotEmptyFields(): Boolean {
        if (client_name.text.toString() == "" || client_phone_number.text.toString() == "") {
            showSnackBar("Заполните поля")
            return false
        }
        return true
    }

    override fun showSuccessOrder() {
        showSnackBar("Ваш заказ успешно выполнен")
        Handler().postDelayed({
            dismiss()
        }, 2500)
    }

    override fun showEmptyData() {
        client_name.setText("Такой продукт в данное время отсутвует")
        client_name.isEnabled  = false
        container_product.visibility  = View.GONE
        order_with_delivery.visibility = View.GONE
        client_phone_number.visibility = View.GONE
        client_phone_warning.visibility = View.GONE
        order_button.isEnabled = false
    }

    private fun showSnackBar(text: String) {
        val snack = Snackbar.make(mView, text, Snackbar.LENGTH_SHORT)
        snack.show()
    }

    override fun cancelOrder() {
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    companion object {
        fun newInstance(product: Product): BuyFragment {
            val fragment = BuyFragment()
            val args = Bundle()
            args.putParcelable(PRODUCT_BUY_ARG, product)
            fragment.arguments = args
            return fragment
        }

    }
}