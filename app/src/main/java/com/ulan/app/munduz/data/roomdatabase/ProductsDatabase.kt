package com.ulan.app.munduz.data.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ProductEntity::class), version = 1, exportSchema = false )
abstract class ProductsDatabase : RoomDatabase(){

    abstract fun productsDao(): DaoProducts

}