package ulanapp.munduz.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    var email: String = "",
    var subject: String = "",
    var body: String = ""
) : Parcelable
