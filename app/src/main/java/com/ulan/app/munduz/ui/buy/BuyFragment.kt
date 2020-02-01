package com.ulan.app.munduz.ui.buy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.model.Message
import com.ulan.app.munduz.data.model.Order
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_BUY_ARG
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.buy_layout.*
import javax.inject.Inject

class BuyFragment : BaseDialogFragment(), BuyView{

    @Inject
    lateinit var mPresenter: BuyPresenter

    @Inject
    lateinit var mSendEmailHelper: SendEmailHelper

    private lateinit var mProduct: Product

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mProduct = arguments!!.getParcelable(PRODUCT_BUY_ARG)
        return inflater.inflate(R.layout.buy_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.setProduct(mProduct)

        mPresenter.setSendEmailHelper(mSendEmailHelper)
        order_product_name.text=mProduct.name

        order_button.setOnClickListener{
            mPresenter.sendButtonClicked()
        }

        cancel_button.setOnClickListener{
            mPresenter.cancelButtonClicked()
        }


        order_increment.setOnClickListener{
            order_count.text = incrementProduct().toString()
        }

        order_decrement.setOnClickListener{
            order_count.text = decrementProduct().toString()
        }

    }

    private fun incrementProduct(): Int{
        var count = order_count.text.toString().toInt()
        count = count.inc()
        return count
    }

    private fun decrementProduct(): Int{
        var count = order_count.text.toString().toInt()
        if(count == 1){
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

    override fun cancelOrder() {
        dismiss()
    }

    override fun isNotEmptyFields() : Boolean{
        if(client_name.text.toString() == "" || client_phone_number.text.toString() == ""){
            isNotEmptyFields()
            return true
        }
        return false
    }

    override fun showSuccessOrder() {
        Toast.makeText(activity!!, "Ваш заказ успешно выполнен", Toast.LENGTH_SHORT).show()
    }

    companion object{
        fun newInstance(product: Product): BuyFragment{
            val fragment = BuyFragment()
            val args = Bundle()
            args.putParcelable(PRODUCT_BUY_ARG, product)
            fragment.arguments = args
            return fragment
        }
    }
}