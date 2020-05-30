package com.ulan.app.munduz.data.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "purchases_table", indices = [Index(value = ["id"], unique = true)])
data class PurchaseEntity(
    @PrimaryKey
    var id: String = "",
    var category: String = "",
    var name: String = "",
    var desc: String = "",
    var price: Int = -1,
    var priceInc: Int = -1,
    var perPrice: String = "",
    var perPriceInc: String = "",
    @Embedded
    var picture: Picture = Picture("url")
) : Parcelable