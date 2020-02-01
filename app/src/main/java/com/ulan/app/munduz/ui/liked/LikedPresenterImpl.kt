package com.ulan.app.munduz.ui.liked

import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.data.roomdatabase.LikedDatabase
import com.ulan.app.munduz.data.roomdatabase.KeyEntity
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.listeners.ProductCallback
import javax.inject.Inject

class LikedPresenterImpl : LikedPresenter{


    private var mView: LikedView?
    private var mDatabase: LikedDatabase
    private var mRepository: Repository

    @Inject
    constructor(mView: LikedView, mDatabase: LikedDatabase, mRepository: Repository) {
        this.mView = mView
        this.mDatabase = mDatabase
        this.mRepository = mRepository
    }

    override fun detachView() {
        mView = null
    }

    override fun initToolbar() {
        mView?.showToolbar()
    }

    override fun loadProducts() {
        var products = mutableListOf<Product>()
        val keys = mDatabase.keysDao().fetchAllKeys()
        for(item: KeyEntity in keys){
            mRepository.loadLikedProduct(item.key, object : ProductCallback{
                override fun onCallback(product: Product) {
                    products.add(product)
                    mView?.showLikedProducts(products)
                }
            })
        }

    }

    override fun deleteButtonClicked() {

    }
}