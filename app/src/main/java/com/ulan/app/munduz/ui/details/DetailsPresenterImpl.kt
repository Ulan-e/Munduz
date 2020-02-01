package com.ulan.app.munduz.ui.details

import android.util.Log
import com.ulan.app.munduz.data.roomdatabase.KeyEntity
import com.ulan.app.munduz.data.roomdatabase.LikedDatabase
import com.ulan.app.munduz.developer.Product
import javax.inject.Inject

class DetailsPresenterImpl : DetailsPresenter {

    private var mView: DetailsView?
    private var mDatabase: LikedDatabase
    private lateinit var mProduct: Product

    @Inject
    constructor(mView: DetailsView, database: LikedDatabase) {
        this.mView = mView
        this.mDatabase = database
    }

    override fun setProduct(product: Product) {
        if(product != null) {
            this.mProduct = product
            mView?.showProduct(mProduct)
        }else {
            mView?.showNoProduct()
        }
    }

    override fun setToolbar() {
        mView?.initToolbar()
    }

    override fun isFavoriteProduct() {
        val key = mProduct.id
        val table = mDatabase.keysDao().fetchAllKeys()
        for (item in table) {
            if (key == item.key) {
                mView?.markAsLiked()
            }
        }
    }

    override fun buyButtonClicked() {
        mView?.showOrderProduct()
    }

    override fun favoriteButtonClicked() {
        val key = mProduct.id
        val table = mDatabase.keysDao().fetchAllKeys()
        if (table.isEmpty()) {
            mDatabase.keysDao().insertKey(getProductKey())
        } else {
            for (item in table) {
                if (key != item.key) {
                    mDatabase.keysDao().insertKey(getProductKey())
                }
            }
        }

    }

    private fun getProductKey(): KeyEntity {
        val keyEntity = KeyEntity()
        keyEntity.key = this.mProduct.id
        return keyEntity
    }

    override fun onBackPressed() {
        mView?.closeDetails()

    }

    override fun detachView() {
        mView = null
    }

}