package com.ulan.app.munduz.data.repository

import com.ulan.app.munduz.data.model.Order
import com.ulan.app.munduz.listeners.ProductListCallback
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductCallback
import com.ulan.app.munduz.listeners.ProductsCallback
import com.ulan.app.munduz.listeners.SliderImagesCallback

interface Repository{

    fun insertOrder(order: Order)
    fun loadProducts(callback: ProductListCallback)
    fun loadSearchedProducts(callback: ProductsCallback)
    fun loadNewProducts(callback: ProductListCallback)
    fun loadLikedProduct(key: String, callback: ProductCallback)
    fun loadFilterProducts(category: String, callback: ProductListCallback)
    fun loadSliderPhotos(callback: SliderImagesCallback)
    fun loadCatalogs() : MutableList<String>
}