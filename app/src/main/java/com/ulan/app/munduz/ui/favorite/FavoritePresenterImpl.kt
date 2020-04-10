package com.ulan.app.munduz.ui.favorite

import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.room.entities.KeyEntity
import com.ulan.app.munduz.data.room.repository.KeysRepositoryImpl
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductCallback
import javax.inject.Inject

class FavoritePresenterImpl : FavoritePresenter {

    private var mView: FavoriteView?
    private var mFirebaseRepository: FirebaseRepository
    private var mKeysRepository: KeysRepositoryImpl
    private var products = ArrayList<Product>()

    @Inject
    constructor(mView: FavoriteView, mRepository: FirebaseRepository, keysRepository: KeysRepositoryImpl) {
        this.mView = mView
        this.mFirebaseRepository = mRepository
        this.mKeysRepository = keysRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProducts() {
        val keys = mKeysRepository.fetchAllKeys()
        products.clear()
        for (item: KeyEntity in keys) {
            mFirebaseRepository.loadLikedProduct(item.key, object : ProductCallback {
                override fun onCallback(product: Product) {
                    products.add(product)
                    if (products.size > 0) {
                        mView?.showLikedProducts(products)
                    } else {
                        mView?.showEmptyData()
                    }
                }
            })
        }
    }

    override fun detachView() {
        mView = null
    }
}