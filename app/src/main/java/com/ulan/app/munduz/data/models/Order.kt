package com.ulan.app.munduz.data.models

data class Order(

    var purchases: String = "",
    var amountPurchases: Int = -1,
    var purchaseMethod: String = "",
    var clientName: String = "",
    var clientPhoneNumber: String = "",
    var clientPhoneNumberSecond: String = "",
    var comment: String = ""

)