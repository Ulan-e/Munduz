package com.ulan.app.munduz.listeners

import com.ulan.app.munduz.developer.Product

interface ProductsCallback {

    fun onCallback(values: MutableList<Product>)

}