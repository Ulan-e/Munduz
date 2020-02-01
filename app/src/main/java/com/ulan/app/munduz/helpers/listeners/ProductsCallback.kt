package com.ulan.app.munduz.helpers.listeners

import com.ulan.app.munduz.developer.Product

interface ProductsCallback{
    fun onCallback(products: ArrayList<Product>)
}