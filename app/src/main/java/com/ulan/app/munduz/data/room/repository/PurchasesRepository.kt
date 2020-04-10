package com.ulan.app.munduz.data.room.repository

import com.ulan.app.munduz.data.room.entities.PurchaseEntity

interface PurchasesRepository {

    fun insert(purchase: PurchaseEntity)
    fun isExist(purchase: PurchaseEntity): Boolean
    fun fetchPurchases(): ArrayList<PurchaseEntity>
    fun sumOfPurchases() : Int
    fun remove(purchase: PurchaseEntity)
    fun removeAll()

}