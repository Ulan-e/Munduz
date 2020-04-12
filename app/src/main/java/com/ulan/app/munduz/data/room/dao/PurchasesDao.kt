package com.ulan.app.munduz.data.room.dao

import androidx.room.*
import com.ulan.app.munduz.data.models.PurchaseEntity

@Dao
interface PurchasesDao {

    @Query("SELECT * FROM purchases_table")
    fun fetchAllPurchases(): List<PurchaseEntity>

    @Query("SELECT SUM(purchases_table.priceInc) FROM purchases_table")
    fun sumOfPurchases(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPurchase(purchaseEntity: PurchaseEntity)

    @Update
    fun updatePurchase(purchaseEntity: PurchaseEntity)

    @Delete
    fun removePurchase(purchaseEntity: PurchaseEntity)

    @Query("DELETE FROM purchases_table")
    fun removeAllPurchases()
}