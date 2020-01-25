package com.ulan.app.munduz.ui.liked

import com.ulan.app.munduz.developer.Product

interface LikedView {

    fun showToolbar()
    fun showLikedProducts(products: MutableList<Product>)
    fun showNoLikedProducts()
}