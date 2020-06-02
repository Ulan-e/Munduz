package ulanapp.munduz.ui.activities.search

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.ui.base.BaseView

interface SearchView : BaseView {

    fun showToolbar()

    fun showProducts(products: MutableList<Product>)

}