package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.data.models.Picture
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.ALREADY_IN_BASKET
import com.ulan.app.munduz.helpers.Constants.Companion.NOT_IN_BASKET
import javax.inject.Inject

class DetailsPresenterImpl : DetailsPresenter {

    private var mView: DetailsView?
    private var mPurchaseRepository: PurchasesRepository
    private var mFavoriteRepository: FavoritesRepository
    private lateinit var mProduct: Product

    @Inject
    constructor(
        mView: DetailsView,
        repository: PurchasesRepository,
        keysRepository: FavoritesRepository
    ) {
        this.mView = mView
        this.mPurchaseRepository = repository
        this.mFavoriteRepository = keysRepository
    }

    override fun setProduct(product: Product) {
        if (product != null) {
            this.mProduct = product
            mView?.showProduct(mProduct)
            mView?.changeAddToBasketText(getBasketText())
        } else {
            mView?.showEmptyData()
        }
    }

    private fun getBasketText(): String {
        if (mPurchaseRepository.isExist(mProduct.id)) {
            return NOT_IN_BASKET
        } else {
            return NOT_IN_BASKET
        }
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun isFavoriteProduct() {
        if (mFavoriteRepository.isExist(mProduct.id)) {
            mView?.markAsFavorite()
        }
    }

    override fun isInAlreadyInBasket() {
        if (mPurchaseRepository.isExist(mProduct.id)) {
            mView?.changeAddToBasketText(ALREADY_IN_BASKET)
        } else {
            mView?.changeAddToBasketText(NOT_IN_BASKET)
        }
    }

    override fun favoriteClicked() {
        if (mFavoriteRepository.isExist(mProduct.id)) {
            mFavoriteRepository.remove(mProduct.id)
            mView?.markAsNotFavorite()
        } else {
            mFavoriteRepository.insert(mProduct.id)
            mView?.markAsFavorite()
        }
    }

    override fun unFavoriteClicked() {
        mFavoriteRepository.remove(mProduct.id)
    }

    override fun onBackPressed() {
        mView?.closeDetails()
    }

    override fun addToBasketClicked() {
        if (mPurchaseRepository.isExist(mProduct.id)) {
            mView?.goToBasket()
        } else {
            mPurchaseRepository.insert(mProduct)
            mView?.changeAddToBasketText(ALREADY_IN_BASKET)
        }
    }

    override fun detachView() {
        mView = null
    }
}