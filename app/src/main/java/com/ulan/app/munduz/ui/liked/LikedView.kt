package com.ulan.app.munduz.ui.liked

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface LikedView : BaseView {
    fun showLikedProducts(products: MutableList<Product>)
}