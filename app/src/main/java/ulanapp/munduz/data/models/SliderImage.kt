package ulanapp.munduz.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SliderImage(
    var name: String = "",
    var image: String = ""
) : Parcelable