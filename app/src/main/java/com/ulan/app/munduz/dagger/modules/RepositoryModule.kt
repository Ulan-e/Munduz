package com.ulan.app.munduz.dagger.modules

import android.content.Context
import androidx.room.Room
import com.ulan.app.munduz.dagger.app.AppModule
import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.data.repository.FirebaseRepositoryImpl
import com.ulan.app.munduz.data.room.MunduzDatabase
import com.ulan.app.munduz.data.room.dao.FavoritesDao
import com.ulan.app.munduz.data.room.dao.PurchasesDao
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.data.room.repository.FavoritesRepositoryImpl
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    fun firebaseRepository(): FirebaseRepository {
        return FirebaseRepositoryImpl()
    }
}

@Module(includes = [AppModule::class])
class RoomModule {

    @Provides
    fun database(context: Context): MunduzDatabase {
        return Room.databaseBuilder(
            context,
            MunduzDatabase::class.java,
            "database_ulan123"
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun favoritesDao(database: MunduzDatabase): FavoritesDao {
        return database.favoritesDao()
    }

    @Provides
    fun purchasesDao(database: MunduzDatabase): PurchasesDao {
        return database.purchasesDao()
    }

    @Provides
    fun keysRepository(favoriteKeys: FavoritesDao): FavoritesRepository {
        return FavoritesRepositoryImpl(
            favoriteKeys
        )
    }

    @Provides
    fun purchaseRepository(purchaseKeys: PurchasesDao): PurchasesRepository {
        return PurchasesRepositoryImpl(
            purchaseKeys
        )
    }

}