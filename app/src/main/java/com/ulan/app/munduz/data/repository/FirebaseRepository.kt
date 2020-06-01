package com.ulan.app.munduz.data.repository

import com.ulan.app.munduz.interfaces.ProductCallback
import com.ulan.app.munduz.interfaces.ProductsCallback
import com.ulan.app.munduz.interfaces.SliderImagesCallback

interface FirebaseRepository {

    fun loadProducts(callback: ProductsCallback)

    fun loadProductsByRecommendation(callback: ProductsCallback)

    fun loadProductsByCategory(category: String, callback: ProductsCallback)

    fun loadProductByKey(key: String, callback: ProductCallback)


    fun loadSliderPhotos(callback: SliderImagesCallback)
}