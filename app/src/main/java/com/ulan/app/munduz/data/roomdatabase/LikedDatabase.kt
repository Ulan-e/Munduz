package com.ulan.app.munduz.data.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [KeyEntity::class], version = 1, exportSchema = false)
abstract class LikedDatabase : RoomDatabase() {

    abstract fun productsDao(): DaoKeys

    companion object {
        var instance: LikedDatabase? = null

        fun getDatabase(context: Context): LikedDatabase? {
            if (instance == null) {
                synchronized(LikedDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LikedDatabase::class.java,
                        "database_mun"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return instance
        }

        fun destroyDatabase(){
            instance = null
        }

    }
}