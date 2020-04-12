package com.ulan.app.munduz.ui.buy

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.google.android.material.snackbar.Snackbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.models.Order
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_AMOUNT_ARG
import com.ulan.app.munduz.helpers.Constants.Companion.EXTRA_PURCHASES_BUY_ARG
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.buy_layout.*
import javax.inject.Inject
import javax.inject.Provider

class BuyFragment : BaseDialogFragment(), BuyView {

    @Inject
    lateinit var mPresenter: BuyPresenter

    @Inject
    lateinit var mSendEmailHelper: Provider<SendEmailHelper>

    private lateinit var mPurchases: MutableList<PurchaseEntity>
    private var mAmount: Int = 0
    private lateinit var mView: View
    private lateinit var mRadioButtonText: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPurchases = arguments!!.getParcelableArrayList(EXTRA_PURCHASES_BUY_ARG)
        mAmount = arguments!!.getInt(EXTRA_PRODUCT_AMOUNT_ARG)
        mView = inflater.inflate(R.layout.buy_layout, container, false)
        val radioGroup = mView.findViewById<RadioGroup>(R.id.order_is_with_delivery)
        radioGroup.setOnCheckedChangeListener {group, checkedId ->
            if (R.id.delivery == checkedId){
                mRadioButtonText = activity!!.resources.getString(R.string.delivery)
            }else{
                mRadioButtonText = activity!!.resources.getString(R.string.pickup)
            }
        }
        return mView
    }

    override fun showToolbar() {
        title_buy_fragment.text = activity!!.resources.getString(R.string.order)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        order.clientName = client_name.text.toString()
        order.clientPhoneNumber = client_phone_number.text.toString()
        order.comment = client_comment.text.toString()
        val time = System.currentTimeMillis()
        order.purchaseMethod = mRadioButtonText
        order.purchases = humanReadableArray(mPurchases).toString()
        order.amountPurchases = mAmount
        return order
    }

    override fun isNotEmptyFields(): Boolean {
        if (client_name.text.toString() == "" || client_phone_number.text.toString() == "") {
            var message = activity!!.resources.getString(R.string.empty_fields)
            showSnackBar(message)
            return false
        }
        return true
    }

    override fun successOrder() {
        var successMessage = activity!!.resources.getString(R.string.success_order)
        showSnackBar(successMessage)
        Handler().postDelayed({
            dismiss()
        }, 2500)
    }

    override fun showEmptyData() {

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
        fun newInstance(purchases: MutableList<PurchaseEntity>, amount: Int): BuyFragment {
            val fragment = BuyFragment()
            val args = Bundle()
            args.putParcelableArrayList(EXTRA_PURCHASES_BUY_ARG, ArrayList(purchases))
            args.putInt(EXTRA_PRODUCT_AMOUNT_ARG, amount)
            fragment.arguments = args
            return fragment
        }
    }
}