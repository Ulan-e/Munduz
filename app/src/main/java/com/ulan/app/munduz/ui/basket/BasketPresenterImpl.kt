package com.ulan.app.munduz.ui.basket

import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.room.entities.PurchaseEntity
import com.ulan.app.munduz.data.room.repository.PurchasesRepositoryImpl
import javax.inject.Inject

class BasketPresenterImpl : BasketPresenter {

    private var mView: BasketView?
    private var mFirebaseRepository: FirebaseRepository
    private var mPurchasesRepository: PurchasesRepositoryImpl
    private var mProducts = ArrayList<PurchaseEntity>()
    private var mSum = 0

    @Inject
    constructor(
        view: BasketView,
        firebaseRepository: FirebaseRepository,
        purchasesRepository: PurchasesRepositoryImpl
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
        mProducts.clear()
        mView?.showAllProducts(purchases)
        mView?.showPurchaseButton()
    }

    override fun purchaseAllButtonClicked() {
        if (mProducts.size > 0) {
            mView?.purchaseAll(mProducts, mSum)
        }
    }

    override fun countSumOfPurchases() {
        this.mSum = mPurchasesRepository.sumOfPurchases()
    }

    override fun decrementCount(price: Int) {
        var result = mSum - price
        if (result == 0) {
            mView?.hidePurchaseButton()
        }
        mView?.showSumOfPurchases(price)
    }

    override fun incrementProduct(price: Int) {
        var result = mSum + price
        mView?.showSumOfPurchases(price)
    }

    override fun detachView() {
        mView = null
    }

}