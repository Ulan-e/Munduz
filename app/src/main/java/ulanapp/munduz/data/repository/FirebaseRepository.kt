package ulanapp.munduz.data.repository

import ulanapp.munduz.interfaces.ProductCallback
import ulanapp.munduz.interfaces.ProductsCallback
import ulanapp.munduz.interfaces.SliderImagesCallback

interface FirebaseRepository {

    fun loadProducts(callback: ProductsCallback)

    fun loadProductsByRecommendation(callback: ProductsCallback)

    fun loadProductsByCategory(category: String, callback: ProductsCallback)

    fun loadProductByKey(key: String, callback: ProductCallback)


    fun loadSliderPhotos(callback: SliderImagesCallback)
}