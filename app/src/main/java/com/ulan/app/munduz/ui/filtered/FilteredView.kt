package com.ulan.app.munduz.ui.filtered

import com.ulan.app.munduz.developer.Product

interface FilteredView {

    fun showProgress()
    fun hideProgress()
    fun showProducts(products: MutableList<Product>)
    fun showNoProducts()
}