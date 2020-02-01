package com.ulan.app.munduz.helpers.listeners

import com.ulan.app.munduz.developer.Product

interface ProductCallback{

    fun onCallback(product: Product)
}