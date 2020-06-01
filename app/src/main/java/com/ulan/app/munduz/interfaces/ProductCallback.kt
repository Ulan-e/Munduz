package com.ulan.app.munduz.interfaces

import com.ulan.app.munduz.developer.Product

interface ProductCallback{

    fun onCallback(product: Product)
}