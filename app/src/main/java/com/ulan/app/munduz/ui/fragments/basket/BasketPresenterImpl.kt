package com.ulan.app.munduz.ui.fragments.basket

import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.ui.base.BasePresenter
import javax.inject.Inject

class BasketPresenterImpl : BasePresenter<BasketView>, BasketPresenter {

    private var firebaseRepository: FirebaseRepository
    private var purchasesRepository: PurchasesRepository
    private var amount = 0

    @Inject
    constructor(firebaseRepository: FirebaseRepository, purchasesRepository: PurchasesRepository) {
        this.firebaseRepository = firebaseRepository
        this.purchasesRepository = purchasesRepository
    }

    override fun loadProducts() {
        val purchases = purchasesRepository.fetchAll()
        val sum = purchasesRepository.purchasesAmount()
        if (purchases.size > 0) {
            getView()?.showProducts(purchases)
            getView()?.showPurchaseButton()
            getView()?.showAmountPurchases(sum)
        } else {
            getView()?.showEmptyData()
            getView()?.hidePurchaseButton()
        }
    }

    override fun purchaseButtonClicked() {
        val purchases = purchasesRepository.fetchAll()
        for (num in purchases) {
            amount += num.priceInc
        }
        getView()?.purchaseAll(purchases, amount)
    }

    override fun goToHomeButtonClicked() {
        getView()?.showGoToHome()
    }

    override fun purchasesAmountChanged() {
        amount = purchasesRepository.purchasesAmount()
        if (amount == 0) {
            getView()?.hidePurchaseButton()
            getView()?.showEmptyData()
        } else {
            getView()?.showAmountPurchases(amount)
        }
    }

}