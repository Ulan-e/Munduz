package com.ulan.app.munduz.data.roomdatabase

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DaoProducts {

    @Query("SELECT * FROM products ")
    fun getLikedProducts() : List<ProductEntity>
}