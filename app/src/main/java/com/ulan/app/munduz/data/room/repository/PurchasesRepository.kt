package com.ulan.app.munduz.data.room.repository

import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.developer.Product

interface PurchasesRepository {

    fun insert(product: Product)

    fun update(purchase: PurchaseEntity)

    fun fetchAll(): MutableList<PurchaseEntity>

    fun remove(key: String)

    fun removeAll()

    fun isExist(key: String): Boolean

    fun purchasesAmount(): Int

}