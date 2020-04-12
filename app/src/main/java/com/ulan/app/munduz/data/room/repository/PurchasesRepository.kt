package com.ulan.app.munduz.data.room.repository

import com.ulan.app.munduz.data.models.PurchaseEntity

interface PurchasesRepository {

    fun insert(purchase: PurchaseEntity)
    fun isExist(purchase: PurchaseEntity): Boolean
    fun isExistId(key: String): Boolean
    fun update(purchase: PurchaseEntity)
    fun fetchPurchases(): MutableList<PurchaseEntity>
    fun remove(purchase: PurchaseEntity)
    fun removeAll()
    fun sumOfPurchases() : Int

}