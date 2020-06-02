package ulanapp.munduz.data.room.dao

import androidx.room.*
import ulanapp.munduz.data.models.PurchaseEntity

@Dao
interface PurchasesDao {

    @Query("SELECT * FROM purchases_table")
    fun fetchPurchases(): List<PurchaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(purchaseEntity: PurchaseEntity)

    @Update
    fun update(purchaseEntity: PurchaseEntity)

    @Delete
    fun remove(purchaseEntity: PurchaseEntity)

    @Query("DELETE FROM purchases_table")
    fun removePurchases()

    @Query("SELECT SUM(purchases_table.priceInc) FROM purchases_table")
    fun purchasesAmount(): Int
}