package ulanapp.munduz.data.room.repository

import ulanapp.munduz.data.models.FavoriteEntity

interface FavoritesRepository {

    fun insert(key: String)

    fun fetchAll(): MutableList<FavoriteEntity>

    fun remove(key: String)

    fun removeAll()

    fun isExist(key: String): Boolean

}