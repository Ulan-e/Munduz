package ulanapp.munduz.data.room.repository

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.models.PurchaseEntity

interface PurchasesRepository {

    fun insert(product: Product)

    fun update(purchase: PurchaseEntity)

    fun fetchAll(): MutableList<PurchaseEntity>

    fun remove(key: String)

    fun removeAll()

    fun isExist(key: String): Boolean

    fun purchasesAmount(): Int

}