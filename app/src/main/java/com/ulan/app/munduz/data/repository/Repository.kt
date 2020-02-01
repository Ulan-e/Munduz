package com.ulan.app.munduz.data.repository

import com.ulan.app.munduz.data.model.Order
import com.ulan.app.munduz.helpers.listeners.*

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