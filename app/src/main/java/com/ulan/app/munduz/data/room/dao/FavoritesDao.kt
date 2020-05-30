package com.ulan.app.munduz.data.room.dao

import androidx.room.*
import com.ulan.app.munduz.data.models.FavoriteEntity

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorite_table")
    fun fetchFavorites(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteEntity): Long

    @Delete
    fun remove(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite_table")
    fun removeFavorites()
}