package com.ulan.app.munduz.data.repository

import com.ulan.app.munduz.listeners.ProductCallback
import com.ulan.app.munduz.listeners.ProductsCallback
import com.ulan.app.munduz.listeners.SliderImagesCallback

interface FirebaseRepository {

    fun loadProducts(callback: ProductsCallback)

    fun loadSearchedProducts(callback: ProductsCallback)

    fun loadRecommendedProducts(callback: ProductsCallback)

    fun loadFilteredProducts(category: String, callback: ProductsCallback)


    fun loadLikedProduct(key: String, callback: ProductCallback)

    fun loadSliderPhotos(callback: SliderImagesCallback)
}