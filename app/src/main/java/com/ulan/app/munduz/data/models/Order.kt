package com.ulan.app.munduz.data.models

data class Order(

    var purchases: String = "",
    var amountPurchases: Int = -1,
    var isWithDelivery: String = "",
    var clientName: String = "",
    var clientPhoneNumber: String = "",
    var comment: String = ""

)