package com.ulan.app.munduz.ui.search

import com.ulan.app.munduz.developer.Product

interface SearchView{

    fun showProducts(products: ArrayList<Product>)
    fun showNoProducts(message: String)
}