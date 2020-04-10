package com.ulan.app.munduz.data.room.dao

import androidx.room.*
import com.ulan.app.munduz.data.room.entities.KeyEntity
import com.ulan.app.munduz.developer.Product

@Dao
interface KeysDao {

    @Query("SELECT * FROM keys_table ")
    fun fetchAllKeys() : List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKey(product: Product)

    @Delete
    fun removeKey(product: Product)

    @Query("DELETE FROM keys_table")
    fun removeAllKeys()
}