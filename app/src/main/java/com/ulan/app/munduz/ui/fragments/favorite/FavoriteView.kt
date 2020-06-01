package com.ulan.app.munduz.ui.fragments.favorite

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface FavoriteView : BaseView {

    fun showProducts(products: MutableList<Product>)

}