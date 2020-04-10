package com.ulan.app.munduz.developer

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ulan.app.munduz.data.model.Picture
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "purchases_table", indices = [Index(value = ["key"], unique = true)])
data class Product(
    @PrimaryKey
    var key: String = "",
    var category: String = "",
    var name: String = "",
    var desc: String = "",
    var cost: Int = -1,
    var isVisible: Boolean = false,
    var priceFor: String = "",
    @Embedded
    var picture: Picture = Picture("url"),
    var date: Long = 0L
) : Parcelable



