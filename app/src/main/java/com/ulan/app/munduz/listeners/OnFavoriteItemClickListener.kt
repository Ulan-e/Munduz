package com.ulan.app.munduz.listeners

import com.ulan.app.munduz.developer.Product

interface OnFavoriteItemClickListener {

    fun onItemClick(product: Product?)
    fun onBuyClick(product: Product?)

}