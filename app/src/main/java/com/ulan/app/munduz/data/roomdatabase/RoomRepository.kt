package com.ulan.app.munduz.data.roomdatabase

interface RoomRepository {

    fun insert(key: String)
    fun remove(key: String)
    fun removeAll()
    fun isLiked(key: String): Boolean
    fun isKeyExists(key: String): Boolean
}