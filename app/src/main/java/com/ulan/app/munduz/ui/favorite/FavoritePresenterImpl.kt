package com.ulan.app.munduz.ui.favorite

import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.data.models.FavoriteEntity
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductCallback
import javax.inject.Inject

class FavoritePresenterImpl : FavoritePresenter {

    private var view: FavoriteView?
    private var firebaseRepository: FirebaseRepository
    private var favoriteRepository: FavoritesRepository
    private var products = mutableListOf<Product>()

    @Inject
    constructor(view: FavoriteView, firebase: FirebaseRepository, favorites: FavoritesRepository) {
        this.view = view
        this.firebaseRepository = firebase
        this.favoriteRepository = favorites
    }

    override fun setToolbar() {
        view?.showToolbar()
    }

    override fun loadProducts() {
        val keys = favoriteRepository.fetchAll()
        if (keys.size > 0) {
            for (item: FavoriteEntity in keys) {
                firebaseRepository.loadProductByKey(item.key, object : ProductCallback {
                    override fun onCallback(product: Product) {
                        products.add(product)
                        view?.showProducts(products)
                    }
                })
            }
        } else {
            view?.showEmptyData()
        }
    }

    override fun detachView() {
        view = null
    }
}