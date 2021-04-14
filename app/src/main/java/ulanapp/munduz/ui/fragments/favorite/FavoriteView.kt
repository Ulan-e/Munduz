package ulanapp.munduz.ui.fragments.favorite

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.ui.base.BaseView

interface FavoriteView : BaseView {

    // показать продукты
    fun showProducts(products: MutableList<Product>)
}