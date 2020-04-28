package com.ulan.app.munduz.developer

import android.os.Parcelable
import com.ulan.app.munduz.data.models.Picture
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var id: String = "",
    var category: String = "",
    var name: String = "",
    var desc: String = "",
    var cost: Int = -1,
    var priceFor: String = "",
    var picture: Picture = Picture(),
    var date: Long = 0L,
    var visibility: Boolean = false,
    var recommend: Boolean = false
) : Parcelable



