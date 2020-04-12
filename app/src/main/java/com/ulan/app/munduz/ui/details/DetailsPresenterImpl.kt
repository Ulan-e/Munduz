package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.data.models.Picture
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.developer.Product
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
        if (mPurchaseRepository.isExistId(mProduct.id)) {
            return "Товар в корзине"
        } else {
            return "Добавить в корзину"
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
        if (mPurchaseRepository.isExist(generateNewPurchase(mProduct))) {
            mView?.changeAddToBasketText("Товар в корзине")
            mView?.goToBasket()
        } else {
            mPurchaseRepository.insert(generateNewPurchase(mProduct))
            mView?.changeAddToBasketText("Товар в корзине")
        }
    }

    private fun generateNewPurchase(product: Product): PurchaseEntity {
        var purchase = PurchaseEntity()
        var picture = Picture()
        purchase.id = product.id
        purchase.name = product.name
        purchase.category = product.category
        purchase.price = product.cost
        purchase.priceIncreased = product.cost
        purchase.perPrice = product.priceFor
        purchase.perPriceIncreased = product.priceFor
        purchase.desc = product.desc
        picture.urlImage = product.picture.urlImage
        purchase.picture = picture
        return purchase
    }

    override fun detachView() {
        mView = null
    }
}