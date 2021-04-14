package ulanapp.munduz.dagger

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ulanapp.munduz.data.repository.FirebaseRepository
import ulanapp.munduz.data.repository.FirebaseRepositoryImpl
import ulanapp.munduz.data.room.MunduzDatabase
import ulanapp.munduz.data.room.dao.FavoritesDao
import ulanapp.munduz.data.room.dao.PurchasesDao
import ulanapp.munduz.data.room.repository.FavoritesRepository
import ulanapp.munduz.data.room.repository.FavoritesRepositoryImpl
import ulanapp.munduz.data.room.repository.PurchasesRepository
import ulanapp.munduz.data.room.repository.PurchasesRepositoryImpl
import ulanapp.munduz.helpers.Constants.Companion.DATABASE_NAME

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
            DATABASE_NAME
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