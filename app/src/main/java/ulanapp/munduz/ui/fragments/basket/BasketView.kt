package ulanapp.munduz.ui.fragments.basket

import ulanapp.munduz.data.models.PurchaseEntity
import ulanapp.munduz.ui.base.BaseView

interface BasketView : BaseView {

    // показать продукты
    fun showProducts(purchases: MutableList<PurchaseEntity>)

    // купить все
    fun purchaseAll(purchases: MutableList<PurchaseEntity>)

    // переход домой
    fun showGoToHome()

    // скрыть кнопку купить
    fun hidePurchaseButton()

    // показать кнопку купить
    fun showPurchaseButton()
}