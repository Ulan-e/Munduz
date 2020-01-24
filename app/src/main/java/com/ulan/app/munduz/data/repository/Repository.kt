package com.ulan.app.munduz.data.repository

import com.ulan.app.munduz.listeners.ProductListCallback
import com.ulan.app.munduz.developer.Product

interface Repository{

    fun loadCatalogs() : MutableList<String>
    fun loadProducts(callback: ProductListCallback)
    fun loadNewProducts(callback: ProductListCallback)
    fun loadFilterProducts(category: String, callback: ProductListCallback)
}