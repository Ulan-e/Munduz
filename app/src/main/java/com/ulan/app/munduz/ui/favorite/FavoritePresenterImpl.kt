package com.ulan.app.munduz.ui.favorite

import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.data.roomdatabase.KeyEntity
import com.ulan.app.munduz.data.roomdatabase.StarredDatabase
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductCallback
import javax.inject.Inject

class FavoritePresenterImpl : FavoritePresenter{

    private var mView: FavoriteView?
    private var mDatabase: StarredDatabase?
    private var mRepository: Repository
    private var products = mutableListOf<Product>()

    @Inject
    constructor(mView: FavoriteView, mDatabase: StarredDatabase, mRepository: Repository) {
        this.mView = mView
        this.mDatabase = mDatabase
        this.mRepository = mRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProducts() {

        val keys = mDatabase!!.keysDao().fetchAllKeys()
            for (item: KeyEntity in keys) {
                mRepository.loadLikedProduct(item.key, object : ProductCallback {
                    override fun onCallback(product: Product) {
                        products.add(product)
                        if (products.size > 0) {
                            mView?.showLikedProducts(products)
                        }
                    }
                })
            }
    }

    override fun deleteButtonClicked() {
        TODO()
    }

    override fun detachView() {
        mView = null
    }
}