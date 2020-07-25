package ulanapp.munduz.ui.fragments.basket

import ulanapp.munduz.data.room.repository.PurchasesRepository
import ulanapp.munduz.interfaces.OnChangeSumListener
import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class BasketPresenterImpl @Inject constructor(
    private var purchasesRepository: PurchasesRepository
) : BasePresenter<BasketView>(), BasketPresenter {

    private lateinit var sumChangeListener: OnChangeSumListener

    fun setListener(listener: OnChangeSumListener) {
        this.sumChangeListener = listener
    }

    override fun loadProducts() {
        val purchases = purchasesRepository.fetchAll()
        val sum = purchasesRepository.purchasesAmount()
        if (purchases.size > 0) {
            getView()?.showProducts(purchases)
            getView()?.showPurchaseButton()
            sumChangeListener.onAmountChanged(sum)
        } else {
            getView()?.showEmptyData()
            getView()?.hidePurchaseButton()
        }
    }

    override fun purchaseButtonClicked() {
        val purchases = purchasesRepository.fetchAll()
        getView()?.purchaseAll(purchases)
    }

    override fun goToHomeButtonClicked() {
        getView()?.showGoToHome()
    }

}