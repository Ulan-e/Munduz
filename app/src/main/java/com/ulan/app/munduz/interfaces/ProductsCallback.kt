package com.ulan.app.munduz.interfaces

import com.ulan.app.munduz.developer.Product

interface ProductsCallback {

    fun onCallback(values: MutableList<Product>)

}