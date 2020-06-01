package com.ulan.app.munduz.ui.fragments.filtered

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface FilteredView : BaseView {

    fun isNetworkOn(): Boolean

    fun showErrorNetwork()

    fun showProducts(products: MutableList<Product>)

}