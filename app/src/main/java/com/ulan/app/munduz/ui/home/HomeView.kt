package com.ulan.app.munduz.ui.home

import com.ulan.app.munduz.data.models.SliderImage
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface HomeView : BaseView {

    fun isNetworkOn(): Boolean
    fun showErrorNetwork()
    fun showProducts(products: MutableList<Product>)
    fun showSliderImages(images: MutableList<SliderImage>)

}