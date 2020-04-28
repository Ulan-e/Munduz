package com.ulan.app.munduz.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(

    var purchases: String = "",
    var amountPurchases: Int = -1,
    var purchaseMethod: String = "",
    var clientName: String = "",
    var clientPhoneNumber: String = "",
    var comment: String = ""

): Parcelable