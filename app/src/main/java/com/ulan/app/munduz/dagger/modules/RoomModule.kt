package com.ulan.app.munduz.dagger.modules

import android.content.Context
import androidx.room.Room
import com.ulan.app.munduz.data.roomdatabase.DaoKeys
import com.ulan.app.munduz.data.roomdatabase.StarredDatabase
import com.ulan.app.munduz.data.roomdatabase.RoomRepository
import com.ulan.app.munduz.data.roomdatabase.RoomRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
class RoomModule {

    @Provides
    fun database(context: Context): StarredDatabase{
        return Room.databaseBuilder(context, StarredDatabase::class.java, "munduz_base")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun daoKeys(database: StarredDatabase): DaoKeys{
        return database.keysDao()
    }

    @Provides
    fun roomRepository(daoKeys: DaoKeys): RoomRepository{
        return RoomRepositoryImpl(daoKeys)
    }

}