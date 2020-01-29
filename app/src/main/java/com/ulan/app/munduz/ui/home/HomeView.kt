package com.ulan.app.munduz.ui.home

import com.ulan.app.munduz.developer.Product

interface HomeView{

    fun showToolbar()
    fun showProgress()
    fun hideProgress()
    fun showProducts(products: MutableList<Product>)
    fun showNoProducts()

}