package ulanapp.munduz.ui.fragments.home

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.models.SliderImage
import ulanapp.munduz.ui.base.BaseView

interface HomeView : BaseView {

    // есть интернет?
    fun isNetworkOn(): Boolean

    // сообщение об ошибке в интернете
    fun showErrorNetwork()

    // показать продукты
    fun showProducts(products: MutableList<Product>)

    // показать изобрадения слайдера
    fun showSliderImages(images: List<SliderImage>)
}