package ulanapp.munduz.ui.fragments.filtered

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.ui.base.BaseView

interface FilteredView : BaseView {

    fun isNetworkOn(): Boolean

    fun showErrorNetwork()

    fun showProducts(products: MutableList<Product>)

}