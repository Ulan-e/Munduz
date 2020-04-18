package com.ulan.app.munduz.data.room.repository

import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.developer.Product

interface PurchasesRepository {

    fun insert(product: Product)
    fun isExist(key: String): Boolean
    fun update(purchase: PurchaseEntity)
    fun fetchPurchases(): MutableList<PurchaseEntity>
    fun remove(key: String)
    fun removeAll()
    fun sumOfPurchases() : Int

}