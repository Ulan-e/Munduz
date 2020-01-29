package com.ulan.app.munduz.dagger.modules

import android.content.Context
import androidx.room.Room
import com.ulan.app.munduz.data.roomdatabase.DaoKeys
import com.ulan.app.munduz.data.roomdatabase.LikedDatabase
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
class RoomModule {

    @Provides
    fun database(context: Context): LikedDatabase{
        return Room.databaseBuilder(context, LikedDatabase::class.java, "munduz_database")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun daoKeys(database: LikedDatabase): DaoKeys{
        return database.keysDao()
    }

}