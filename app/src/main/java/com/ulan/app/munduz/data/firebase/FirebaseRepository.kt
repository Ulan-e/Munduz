package com.ulan.app.munduz.data.firebase

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductCallback
import com.ulan.app.munduz.listeners.ProductsCallback
import com.ulan.app.munduz.listeners.SliderImagesCallback

interface FirebaseRepository {

    fun loadProducts(callback: ProductsCallback)
    fun loadSearchedProducts(callback: ProductsCallback)
    fun loadNewProducts(callback: ProductsCallback)
    fun loadFilterProducts(category: String, callback: ProductsCallback)

    fun updateProduct(product: Product)
    fun loadLikedProduct(key: String, callback: ProductCallback)

    fun loadSliderPhotos(callback: SliderImagesCallback)

    fun loadCatalogs(): MutableList<String>
}