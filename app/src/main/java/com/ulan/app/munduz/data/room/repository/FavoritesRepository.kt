package com.ulan.app.munduz.data.room.repository

import com.ulan.app.munduz.data.models.FavoriteEntity

interface FavoritesRepository {

    fun insert(key: String)

    fun fetchAll(): MutableList<FavoriteEntity>

    fun remove(key: String)

    fun removeAll()

    fun isExist(key: String): Boolean

}