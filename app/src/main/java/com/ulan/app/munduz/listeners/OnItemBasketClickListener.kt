package com.ulan.app.munduz.listeners

import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.developer.Product

interface OnItemBasketClickListener {

    fun onItemClick(purchase: PurchaseEntity)

}