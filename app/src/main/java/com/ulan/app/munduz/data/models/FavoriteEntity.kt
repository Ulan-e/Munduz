package com.ulan.app.munduz.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table", indices = [Index(value = ["key"], unique = true)])
class FavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "key")
    var key: String = ""
)