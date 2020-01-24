package com.ulan.app.munduz.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    var email: String = "",
    var subject: String = "",
    var body: String ="",
    var time: String = ""): Parcelable
