package ulanapp.munduz.ui.activities.orders

import ulanapp.munduz.data.room.repository.PurchasesRepository
import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class OrdersPresenterImpl @Inject constructor(private var repository: PurchasesRepository) :
    BasePresenter<OrdersView>(), OrdersPresenter {

    private var amount = 0

    init {
        amount = this.repository.purchasesAmount()
    }


    override fun isWithDelivery(withDelivery: Boolean) {
        if (withDelivery) {
            amount += 190
            getView()?.showTotalPurchases(amount)
        } else {
            amount -= 190
            getView()?.showTotalPurchases(amount)
        }
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

    override fun getAllPurchases(): String {
        val purchases = repository.fetchAll()
        val result: StringBuilder = java.lang.StringBuilder()
        for (item in purchases) {
            result.append(
                item.name + ", " +
                        item.perPriceInc + ", цена " +
                        item.priceInc + "\n"
            )
        }
        return result.toString()
    }

}