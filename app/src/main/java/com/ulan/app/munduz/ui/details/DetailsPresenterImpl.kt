package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.data.roomdatabase.RoomRepository
import com.ulan.app.munduz.developer.Product
import javax.inject.Inject

class DetailsPresenterImpl : DetailsPresenter {

    private var mView: DetailsView?
    private var mRepository: RoomRepository
    private lateinit var mProduct: Product

    @Inject
    constructor(mView: DetailsView, repository: RoomRepository) {
        this.mView = mView
        this.mRepository = repository
    }

    override fun setProduct(product: Product) {
        if (product != null) {
            this.mProduct = product
            mView?.showProduct(mProduct)
        } else {
            mView?.showEmptyData()
        }
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun isFavoriteProduct() {
        if (mRepository.isKeyExists(mProduct.id)) {
            mView?.markAsLiked()
        }
    }

    override fun buyButtonClicked() {
        mView?.showOrderProduct()
    }

    override fun favoriteClicked() {
        if (mRepository.isKeyExists(mProduct.id)) {
            mRepository.remove(mProduct.id)
            mView?.markAsNotLiked()
        } else {
            mRepository.insert(mProduct.id)
            mView?.markAsLiked()
        }
    }

    override fun unFavoriteClicked() {
        mRepository.remove(mProduct.id)
    }

    override fun onBackPressed() {
        mView?.closeDetails()
    }

    override fun detachView() {
        mView = null
    }
}