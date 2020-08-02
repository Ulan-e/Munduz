package ulanapp.munduz.data.models

import android.os.Parcelable
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
    var visible: Boolean = true,
    var recommend: Boolean = false
) : Parcelable



