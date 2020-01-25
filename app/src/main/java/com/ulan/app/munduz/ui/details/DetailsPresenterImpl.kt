package com.ulan.app.munduz.ui.details

import android.util.Log
import androidx.core.content.ContextCompat
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.roomdatabase.KeyEntity
import com.ulan.app.munduz.data.roomdatabase.LikedDatabase
import com.ulan.app.munduz.developer.Product

class DetailsPresenterImpl : DetailsPresenter {

    private var mView: DetailsView?
    private var mDatabase: LikedDatabase
    private lateinit var mProduct: Product

    constructor(mView: DetailsView, database: LikedDatabase) {
        this.mView = mView
        this.mDatabase = database
    }

    override fun setProduct(product: Product) {
        this.mProduct = product
        mView?.showProduct(mProduct)
    }

    override fun setToolbar() {
        mView?.initToolbar("Details")
    }

    override fun buyButtonClicked() {

    }

    override fun favoriteButtonClicked() {
        val key = mProduct.id
        val table = mDatabase.productsDao().fetchAllKeys()
        if(table.isEmpty()) {
            mDatabase.productsDao().insertKey(getProductKey())
        }else{
            for(item in table){
                if(key != item.key){
                    mDatabase.productsDao().insertKey(getProductKey())
                }else{
                    Log.d("ulanbek", "product.id $key and item.key $item.key is equal ")
                }
            }
        }

    }

    private fun getProductKey():KeyEntity{
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