package com.ulan.app.munduz.ui.buy

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.model.Order
import com.ulan.app.munduz.data.room.entities.PurchaseEntity
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_BUY_ARG
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_SUM_ARG
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

    private lateinit var mPurchases: ArrayList<PurchaseEntity>
    private var mSum: Int = 0
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPurchases = arguments!!.getParcelableArrayList(PRODUCT_BUY_ARG)
        mSum = arguments!!.getInt(PRODUCT_SUM_ARG)
        mView = inflater.inflate(R.layout.buy_layout, container, false)
        return mView
    }

    override fun showToolbar() {
        title_buy_fragment.text = "Заказать"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.setToolbar()
        mPresenter.setProducts(mPurchases)
        mPresenter.setTotal(mSum)
        val sendEmailHelper = mSendEmailHelper.get()
        mPresenter.setSendEmailHelper(sendEmailHelper)

        order_button.setOnClickListener {
            mPresenter.sendButtonClicked()
        }
        cancel_button.setOnClickListener {
            mPresenter.cancelButtonClicked()
        }
    }

    override fun showTotalPurchases(total: String) {
        total_purchase.text = total
    }

    override fun getInputOrder(): Order {
        return Order()
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
        fun newInstance(purchases : ArrayList<PurchaseEntity>, sum: Int): BuyFragment {
            val fragment = BuyFragment()
            val args = Bundle()
            args.putParcelableArrayList(PRODUCT_BUY_ARG, purchases)
            args.putInt(PRODUCT_SUM_ARG, sum)
            fragment.arguments = args
            return fragment
        }

    }
}