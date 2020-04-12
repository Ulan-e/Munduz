package com.ulan.app.munduz.ui.favorite

import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.models.FavoriteEntity
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductCallback
import javax.inject.Inject

class FavoritePresenterImpl : FavoritePresenter {

    private var mView: FavoriteView?
    private var mFirebaseRepository: FirebaseRepository
    private var mFavoriteRepository: FavoritesRepository
    private var mProducts = mutableListOf<Product>()

    @Inject
    constructor(view: FavoriteView, firebase: FirebaseRepository, favorites: FavoritesRepository) {
        this.mView = view
        this.mFirebaseRepository = firebase
        this.mFavoriteRepository = favorites
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProducts() {
        val keys = mFavoriteRepository.fetchAll()
        if (keys.size > 0) {
            for (item: FavoriteEntity in keys) {
                mFirebaseRepository.loadLikedProduct(item.key, object : ProductCallback {
                    override fun onCallback(product: Product) {
                        mProducts.add(product)
                        mView?.showProducts(mProducts)
                    }
                })
            }
        } else {
            mView?.showEmptyData()
        }
    }

    override fun detachView() {
        mView = null
    }
}