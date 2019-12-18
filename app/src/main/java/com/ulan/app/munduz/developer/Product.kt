package com.ulan.app.munduz.developer

data class Product(
    var category: String = "",
    var name: String = "",
    var desc: String = "",
    var cost: Int = -1,
    var isVisible: Boolean = false,
    var image: String = "",
    var date:  Long = -1)