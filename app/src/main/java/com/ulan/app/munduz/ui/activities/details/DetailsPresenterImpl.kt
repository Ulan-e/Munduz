package com.ulan.app.munduz.ui.activities.details

import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.ALREADY_IN_BASKET
import com.ulan.app.munduz.helpers.Constants.Companion.NOT_IN_BASKET
import com.ulan.app.munduz.ui.base.BasePresenter
import javax.inject.Inject

class DetailsPresenterImpl : BasePresenter<DetailsView>, DetailsPresenter {

    private lateinit var product: Product
    private var purchaseRepository: PurchasesRepository
    private var favoriteRepository: FavoritesRepository

    @Inject
    constructor(repository: PurchasesRepository, keysRepository: FavoritesRepository) {
        this.purchaseRepository = repository
        this.favoriteRepository = keysRepository
    }

    override fun setProduct(product: Product) {
        this.product = product
        getView()?.showProduct(this.product)
        getView()?.changeBasketText(getBasketText())
    }

    private fun getBasketText(): String {
        return if (purchaseRepository.isExist(product.id)) {
            NOT_IN_BASKET
        } else {
            NOT_IN_BASKET
        }
    }

    override fun setToolbar() {
        getView()?.showToolbar()
    }

    override fun isFavorite() {
        if (favoriteRepository.isExist(product.id)) {
            getView()?.markAsFavorite()
        }
    }

    override fun isInBasket() {
        if (purchaseRepository.isExist(product.id)) {
            getView()?.changeBasketText(ALREADY_IN_BASKET)
        } else {
            getView()?.changeBasketText(NOT_IN_BASKET)
        }
    }

    override fun favoriteClicked() {
        if (favoriteRepository.isExist(product.id)) {
            favoriteRepository.remove(product.id)
            getView()?.markAsNotFavorite()
        } else {
            favoriteRepository.insert(product.id)
            getView()?.markAsFavorite()
        }
    }

    override fun unfavoriteClicked() {
        favoriteRepository.remove(product.id)
    }

    override fun onBackPressed() {
        getView()?.closeDetails()
    }

    override fun addToBasketClicked() {
        if (purchaseRepository.isExist(product.id)) {
            getView()?.goToBasket()
        } else {
            purchaseRepository.insert(product)
            getView()?.changeBasketText(ALREADY_IN_BASKET)
        }
    }

}