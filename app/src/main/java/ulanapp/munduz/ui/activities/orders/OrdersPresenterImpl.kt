package ulanapp.munduz.ui.activities.orders

import ulanapp.munduz.data.models.PurchaseEntity
import ulanapp.munduz.helpers.RUBLE
import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class OrdersPresenterImpl @Inject constructor() : BasePresenter<OrdersView>(), OrdersPresenter {

    private lateinit var purchases: MutableList<PurchaseEntity>
    private var amount = 0

    override fun setProducts(purchases: MutableList<PurchaseEntity>) {
        this.purchases = purchases
    }

    override fun setPurchasesAmount(amount: Int) {
        this.amount = amount
        val price = "К оплате " + amount.toString() + RUBLE
        getView()?.showTotalPurchases(price)
    }


    override fun sendButtonClicked() {
        if (getView()?.isNotEmptyFields()!!) {
            getView()?.goToPurchaseMethod(getView()?.getInputOrder()!!)
        }
    }

    override fun cancelButtonClicked() {
        getView()?.cancelOrder()
    }

    override fun setToolbar() {
        getView()?.showToolbar()
    }

    override fun getAmount(): Int {
        return this.amount
    }

}