package com.ulan.app.munduz.interfaces

import com.ulan.app.munduz.data.models.PurchaseEntity

interface OnItemBasketClickListener {

    fun onItemClick(purchase: PurchaseEntity)

}