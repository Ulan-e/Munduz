package ulanapp.munduz.ui.fragments.filtered

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.ui.base.BaseView

interface FilteredView : BaseView {

    // есть интернет
    fun isNetworkOn(): Boolean

    // показть ошибку если нет интернета
    fun showErrorNetwork()

    // показать продукты
    fun showProducts(products: MutableList<Product>)
}