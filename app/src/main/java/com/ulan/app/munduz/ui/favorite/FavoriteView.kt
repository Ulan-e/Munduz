package com.ulan.app.munduz.ui.favorite

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface FavoriteView : BaseView {
    fun showLikedProducts(products: ArrayList<Product>)
}