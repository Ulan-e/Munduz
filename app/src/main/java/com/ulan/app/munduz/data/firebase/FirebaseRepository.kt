package com.ulan.app.munduz.data.firebase

import com.ulan.app.munduz.data.model.Order
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.*

interface FirebaseRepository {

    fun insertOrder(order: Order)
    fun loadProducts(callback: ProductListCallback)
    fun loadSearchedProducts(callback: ProductsCallback)
    fun loadNewProducts(callback: ProductListCallback)
    fun loadFilterProducts(category: String, callback: ProductListCallback)

    fun updateProduct(product: Product)
    fun loadLikedProduct(key: String, callback: ProductCallback)

    fun loadSliderPhotos(callback: SliderImagesCallback)

    fun loadCatalogs(): MutableList<String>
}