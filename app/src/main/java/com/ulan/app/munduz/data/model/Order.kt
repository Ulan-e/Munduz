package com.ulan.app.munduz.data.model

data class Order(
    var id:String = "",
    var productKey:String  = "",
    var productName: String = "",
    var productCount: Int = -1,
    var withDelivery: Boolean = true,
    var clientName: String = "",
    var clientPhoneNumber: String = "",
    var comment: String = "",
    var orderTime: Long = 0L
    )