package com.ulan.app.munduz.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ulan.app.munduz.data.room.dao.KeysDao
import com.ulan.app.munduz.data.room.dao.PurchasesDao
import com.ulan.app.munduz.data.room.entities.KeyEntity
import com.ulan.app.munduz.data.room.entities.PurchaseEntity

@Database(entities = [KeyEntity::class, PurchaseEntity::class], version = 1, exportSchema = false)
abstract class StarredDatabase : RoomDatabase() {

    abstract fun keysDao(): KeysDao
    abstract fun purchasesDao(): PurchasesDao

}