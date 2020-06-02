package ulanapp.munduz.ui.fragments.home

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.models.SliderImage
import ulanapp.munduz.ui.base.BaseView

interface HomeView : BaseView {

    fun isNetworkOn(): Boolean

    fun showErrorNetwork()

    fun showProducts(products: MutableList<Product>)

    fun showSliderImages(images: List<SliderImage>)

}