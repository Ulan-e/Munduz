package com.ulan.app.munduz.helpers.listeners

import com.ulan.app.munduz.developer.Product

interface ProductListCallback {
    fun onCallback(value: MutableList<Product>)
}