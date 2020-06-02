package ulanapp.munduz.data.room.repository

import ulanapp.munduz.data.models.FavoriteEntity
import ulanapp.munduz.data.room.dao.FavoritesDao

class FavoritesRepositoryImpl(private val favoritesDao: FavoritesDao) :
    FavoritesRepository {

    override fun insert(key: String) {
        val table = favoritesDao.fetchFavorites()
        if (table.isEmpty()) {
            favoritesDao.insert(generateFavorite(key))
        } else {
            for (item in table) {
                favoritesDao.insert(generateFavorite(key))
            }
        }
    }

    override fun remove(key: String) {
        val table = favoritesDao.fetchFavorites()
        for (item in table) {
            if (key == item.key) {
                favoritesDao.remove(item)
            }
        }
    }

    override fun isExist(key: String): Boolean {
        val table = favoritesDao.fetchFavorites()
        for (item in table) {
            if (key == item.key) {
                return true
            }
        }
        return false
    }

    override fun removeAll() {
        favoritesDao.removeFavorites()
    }

    private fun generateFavorite(productKey: String): FavoriteEntity {
        val favorite = FavoriteEntity()
        favorite.key = productKey
        return favorite
    }

    override fun fetchAll(): MutableList<FavoriteEntity> {
        return favoritesDao.fetchFavorites().toMutableList()
    }
}