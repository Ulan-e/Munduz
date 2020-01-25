package com.ulan.app.munduz.data.roomdatabase

import androidx.room.*

@Dao
interface DaoKeys {

    @Query("SELECT * FROM keys_table ")
    fun fetchAllKeys() : List<KeyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKey(keyEntity: KeyEntity)

    @Delete
    fun removeKey(keyEntity: KeyEntity)

    @Query("DELETE FROM keys_table")
    fun removeAllKeys()
}