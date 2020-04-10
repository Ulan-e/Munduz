package com.ulan.app.munduz.data.room.repository

interface BaseRepository{

    fun insert(key: String)
    fun remove(key: String)
    fun removeAll()
    fun isExist(key: String): Boolean

}