package ulanapp.munduz.interfaces

import ulanapp.munduz.data.models.PurchaseEntity

interface OnItemBasketClickListener {

    fun onItemClick(purchase: PurchaseEntity)
}