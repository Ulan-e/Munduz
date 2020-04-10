package com.ulan.app.munduz.listeners

import com.ulan.app.munduz.data.room.entities.PurchaseEntity

interface PurchaseClickListener {

    fun onItemClick(purchase: PurchaseEntity)

}