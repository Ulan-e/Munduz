package com.ulan.app.munduz.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ulan.app.munduz.data.room.dao.FavoritesDao
import com.ulan.app.munduz.data.room.dao.PurchasesDao
import com.ulan.app.munduz.data.models.FavoriteEntity
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.developer.Product

@Database(entities = [FavoriteEntity::class, PurchaseEntity::class], version = 1, exportSchema = false)
abstract class MunduzDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao
    abstract fun purchasesDao(): PurchasesDao

}