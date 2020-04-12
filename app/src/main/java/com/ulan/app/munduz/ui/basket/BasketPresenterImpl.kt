package com.ulan.app.munduz.ui.basket

import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import javax.inject.Inject

class BasketPresenterImpl : BasketPresenter {

    private var mView: BasketView?
    private var mFirebaseRepository: FirebaseRepository
    private var mPurchasesRepository: PurchasesRepository
    private var mProducts = mutableListOf<PurchaseEntity>()

    @Inject
    constructor(
        view: BasketView,
        firebaseRepository: FirebaseRepository,
        purchasesRepository: PurchasesRepository
    ) {
        this.mView = view
        this.mFirebaseRepository = firebaseRepository
        this.mPurchasesRepository = purchasesRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProducts() {
        val purchases = mPurchasesRepository.fetchPurchases()
        if (purchases.size > 0) {
            mView?.showProducts(purchases)
            mView?.showPurchaseButton()
            var sum = mPurchasesRepository.sumOfPurchases()
            mView?.showPurchasesAmount(sum)
        } else {
            mView?.showEmptyData()
            mView?.hidePurchaseButton()
        }
    }

    override fun purchaseButtonClicked() {
        val purchases = mPurchasesRepository.fetchPurchases()
        var amount = 0
        for (num in purchases) {
            amount += num.priceInc
        }
        mView?.purchaseAll(purchases, amount)
    }

    override fun goToHomeButtonClicked() {
        mView?.showGoToHome()
    }

    override fun purchasesAmountChanged() {
        var amount = mPurchasesRepository.sumOfPurchases()
        if (amount == 0) {
            mView?.hidePurchaseButton()
            mView?.showEmptyData()
        } else {
            mView?.showPurchasesAmount(amount)
        }
    }

    override fun detachView() {
        mView = null
    }

}