package com.ulan.app.munduz.ui.fragments.basket

import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import javax.inject.Inject

class BasketPresenterImpl : BasketPresenter {

    private var view: BasketView?
    private var firebaseRepository: FirebaseRepository
    private var purchasesRepository: PurchasesRepository

    @Inject
    constructor(
        view: BasketView,
        firebaseRepository: FirebaseRepository,
        purchasesRepository: PurchasesRepository
    ) {
        this.view = view
        this.firebaseRepository = firebaseRepository
        this.purchasesRepository = purchasesRepository
    }

    override fun loadProducts() {
        val purchases = purchasesRepository.fetchAll()
        if (purchases.size > 0) {
            view?.showProducts(purchases)
            view?.showPurchaseButton()
            var sum = purchasesRepository.purchasesAmount()
            view?.showAmountPurchases(sum)
        } else {
            view?.showEmptyData()
            view?.hidePurchaseButton()
        }
    }

    override fun purchaseButtonClicked() {
        val purchases = purchasesRepository.fetchAll()
        var amount = 0
        for (num in purchases) {
            amount += num.priceInc
        }
        view?.purchaseAll(purchases, amount)
    }

    override fun goToHomeButtonClicked() {
        view?.showGoToHome()
    }

    override fun purchasesAmountChanged() {
        var amount = purchasesRepository.purchasesAmount()
        if (amount == 0) {
            view?.hidePurchaseButton()
            view?.showEmptyData()
        } else {
            view?.showAmountPurchases(amount)
        }
    }

    override fun detachView() {
        view = null
    }

}