package com.ulan.app.munduz.developer



import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var id: String = "",
    var category: String = "",
    var name: String = "",
    var desc: String = "",
    var cost: Int = -1,
    var isVisible: Boolean = false,
    var image: String = "",
    var date:  Long = -1): Parcelable



