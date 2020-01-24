package com.ulan.app.munduz.data.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.ulan.app.munduz.data.model.Picture

@Entity(tableName = "products")
data class ProductEntity(
    @ColumnInfo(name="id") var id: String,
    @ColumnInfo(name="category") var category: String,
    @ColumnInfo(name="name") var name: String,
    @ColumnInfo(name="desc") var desc: String,
    @ColumnInfo(name="cost") var cost: Int,
    @ColumnInfo(name="amount") var amount: Int,
    @ColumnInfo(name="picture") var picture: Picture = Picture("url"),
    @ColumnInfo(name="date") var date:  Long
)