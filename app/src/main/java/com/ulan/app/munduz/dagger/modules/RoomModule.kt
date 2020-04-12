package com.ulan.app.munduz.dagger.modules

import android.content.Context
import androidx.room.Room
import com.ulan.app.munduz.data.room.StarredDatabase
import com.ulan.app.munduz.data.room.dao.FavoritesDao
import com.ulan.app.munduz.data.room.dao.PurchasesDao
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.data.room.repository.FavoritesRepositoryImpl
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
class RoomModule {

    @Provides
    fun database(context: Context): StarredDatabase {
        return Room.databaseBuilder(context, StarredDatabase::class.java, "database00001")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun favoritesDao(database: StarredDatabase): FavoritesDao {
        return database.favoritesDao()
    }

    @Provides
    fun purchasesDao(database: StarredDatabase): PurchasesDao {
        return database.purchasesDao()
    }

    @Provides
    fun keysRepository(favoriteKeys: FavoritesDao): FavoritesRepository {
        return FavoritesRepositoryImpl(favoriteKeys)
    }

    @Provides
    fun purchaseRepository(purchaseKeys: PurchasesDao): PurchasesRepository {
        return PurchasesRepositoryImpl(purchaseKeys)
    }

}