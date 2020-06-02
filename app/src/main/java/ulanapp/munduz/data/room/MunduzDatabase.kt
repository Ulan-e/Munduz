package ulanapp.munduz.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ulanapp.munduz.data.models.FavoriteEntity
import ulanapp.munduz.data.models.PurchaseEntity
import ulanapp.munduz.data.room.dao.FavoritesDao
import ulanapp.munduz.data.room.dao.PurchasesDao

@Database(
    entities = [
        FavoriteEntity::class,
        PurchaseEntity::class
    ], version = 1, exportSchema = false
)
abstract class MunduzDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao
    abstract fun purchasesDao(): PurchasesDao

}