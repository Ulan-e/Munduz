package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.data.room.entities.PurchaseEntity
import com.ulan.app.munduz.data.room.repository.KeysRepositoryImpl
import com.ulan.app.munduz.data.room.repository.PurchasesRepositoryImpl
import com.ulan.app.munduz.developer.Product
import javax.inject.Inject

class DetailsPresenterImpl : DetailsPresenter {

    private var mView: DetailsView?
    private var mPurchaseRepository: PurchasesRepositoryImpl
    private var mKeysRepository: KeysRepositoryImpl
    private lateinit var mProduct: Product
    private var mPurchase = PurchaseEntity()

    @Inject
    constructor(mView: DetailsView, repository: PurchasesRepositoryImpl, keysRepository: KeysRepositoryImpl) {
        this.mView = mView
        this.mPurchaseRepository = repository
        this.mKeysRepository = keysRepository
    }

    override fun setProduct(product: Product) {
        if (product != null) {
            this.mProduct = product
            setPurchaseEntity(mProduct)
            mView?.showProduct(mProduct)
        } else {
            mView?.showEmptyData()
        }
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun isFavoriteProduct() {
        if (mKeysRepository.isExist(mProduct.key)) {
            mView?.markAsLiked()
        }
    }

    override fun buyButtonClicked() {
        mView?.addToBasket()
    }

    override fun favoriteClicked() {
        if (mKeysRepository.isExist(mProduct.key)) {
            mKeysRepository.remove(mProduct.key)
            mView?.markAsNotLiked()
        } else {
            mKeysRepository.insert(mProduct.key)
            mView?.markAsLiked()
        }
    }

    override fun unFavoriteClicked() {
        mKeysRepository.remove(mProduct.key)
    }

    override fun onBackPressed() {
        mView?.closeDetails()
    }

    override fun addToBasketClicked(id: String) {
        if (mPurchaseRepository.isExist(getProduct())) {
            mView?.showSnackBar("Товар уже в корзине")
        } else {
            mPurchaseRepository.insert(getProduct())
            mView?.showSnackBar("Товар добавлен")
        }
    }

    private fun setPurchaseEntity(product: Product){
        var pictureNew = com.ulan.app.munduz.data.model.Picture()
        pictureNew.urlImage = product.picture.urlImage

        mPurchase.key = product.key
        mPurchase.time = 5555555
        mPurchase.isVisible = product.isVisible
        mPurchase.name = product.name
        mPurchase.cost = product.cost
        mPurchase.priceFor = product.priceFor
        mPurchase.picture = pictureNew
        mPurchase.desc = product.desc
        mPurchase.category = product.category
    }

    private fun getProduct(): PurchaseEntity {
        return mPurchase
    }

    override fun detachView() {
        mView = null
    }
}