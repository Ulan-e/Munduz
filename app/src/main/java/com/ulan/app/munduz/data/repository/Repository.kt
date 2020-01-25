package com.ulan.app.munduz.data.repository

import com.ulan.app.munduz.listeners.ProductListCallback
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductCallback

interface Repository{

    fun loadProducts(callback: ProductListCallback)
    fun loadNewProducts(callback: ProductListCallback)
    fun loadLikedProduct(key: String, callback: ProductCallback)
    fun loadFilterProducts(category: String, callback: ProductListCallback)
    fun loadCatalogs() : MutableList<String>
}