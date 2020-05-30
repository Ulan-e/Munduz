package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.ALREADY_IN_BASKET
import com.ulan.app.munduz.helpers.Constants.Companion.NOT_IN_BASKET
import javax.inject.Inject

class DetailsPresenterImpl : DetailsPresenter {

    private var view: DetailsView?
    private var purchaseRepository: PurchasesRepository
    private var favoriteRepository: FavoritesRepository
    private lateinit var product: Product

    @Inject
    constructor(
        view: DetailsView,
        repository: PurchasesRepository,
        keysRepository: FavoritesRepository
    ) {
        this.view = view
        this.purchaseRepository = repository
        this.favoriteRepository = keysRepository
    }

    override fun setProduct(product: Product) {
        if (product != null) {
            this.product = product
            view?.showProduct(this.product)
            view?.changeAddToBasketText(getBasketText())
        } else {
            view?.showEmptyData()
        }
    }

    private fun getBasketText(): String {
        if (purchaseRepository.isExist(product.id)) {
            return NOT_IN_BASKET
        } else {
            return NOT_IN_BASKET
        }
    }

    override fun setToolbar() {
        view?.showToolbar()
    }

    override fun isFavoriteProduct() {
        if (favoriteRepository.isExist(product.id)) {
            view?.markAsFavorite()
        }
    }

    override fun isInAlreadyInBasket() {
        if (purchaseRepository.isExist(product.id)) {
            view?.changeAddToBasketText(ALREADY_IN_BASKET)
        } else {
            view?.changeAddToBasketText(NOT_IN_BASKET)
        }
    }

    override fun favoriteClicked() {
        if (favoriteRepository.isExist(product.id)) {
            favoriteRepository.remove(product.id)
            view?.markAsNotFavorite()
        } else {
            favoriteRepository.insert(product.id)
            view?.markAsFavorite()
        }
    }

    override fun unFavoriteClicked() {
        favoriteRepository.remove(product.id)
    }

    override fun onBackPressed() {
        view?.closeDetails()
    }

    override fun addToBasketClicked() {
        if (purchaseRepository.isExist(product.id)) {
            view?.goToBasket()
        } else {
            purchaseRepository.insert(product)
            view?.changeAddToBasketText(ALREADY_IN_BASKET)
        }
    }

    override fun detachView() {
        view = null
    }
}