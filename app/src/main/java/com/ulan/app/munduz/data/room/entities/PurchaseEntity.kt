/*
package com.ulan.app.munduz.data.room.entities

import android.os.Parcelable
import androidx.room.*
import com.ulan.app.munduz.data.model.Picture
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "purchases_table", indices = [Index(value = ["key"], unique = true)])
class PurchaseEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "key")
    var key: String = "",

    @ColumnInfo(name = "category")
    var category: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "desc")
    var desc: String = "",

    @ColumnInfo(name = "cost")
    var cost: Int = -1,

    @ColumnInfo(name = "isVisible")
    var isVisible: Boolean = false,

    @ColumnInfo(name = "priceFor")
    var priceFor: String = "",

    @Embedded
    var picture: Picture? = null,

    @ColumnInfo(name = "time")
    var time: Long = 0L
) : Parcelable


*/
