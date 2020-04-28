package com.ulan.app.munduz.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Picture(
    var urlImage: String = "",
    var urlImage2: String = "",
    var urlImage3: String = "") :
    Parcelable