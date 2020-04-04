package com.ulan.app.munduz.data.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [KeyEntity::class], version = 1, exportSchema = false)
abstract class StarredDatabase : RoomDatabase() {

    abstract fun keysDao(): DaoKeys

}