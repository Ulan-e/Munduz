package com.ulan.app.munduz.dagger.modules

import android.content.Context
import androidx.room.Room
import com.ulan.app.munduz.data.room.StarredDatabase
import com.ulan.app.munduz.data.room.dao.KeysDao
import com.ulan.app.munduz.data.room.dao.PurchasesDao
import com.ulan.app.munduz.data.room.repository.KeysRepositoryImpl
import com.ulan.app.munduz.data.room.repository.PurchasesRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
class RoomModule {

    @Provides
    fun database(context: Context): StarredDatabase{
        return Room.databaseBuilder(context, StarredDatabase::class.java, "database_44")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun keysDao(database: StarredDatabase): KeysDao {
        return database.keysDao()
    }

    @Provides
    fun purchasesDao(database: StarredDatabase): PurchasesDao {
        return database.purchasesDao()
    }

    @Provides
    fun keysRepository(daoKeys: KeysDao): KeysRepositoryImpl {
        return KeysRepositoryImpl(
            daoKeys
        )
    }

    @Provides
    fun purchaseRepository(daoKeys: PurchasesDao): PurchasesRepositoryImpl {
        return PurchasesRepositoryImpl(
            daoKeys
        )
    }

}