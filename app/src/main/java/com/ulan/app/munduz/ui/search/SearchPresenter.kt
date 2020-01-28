package com.ulan.app.munduz.ui.search

import com.ulan.app.munduz.developer.Product

interface SearchPresenter{

    fun setProducts(products: ArrayList<Product>)
    fun detachView()
    fun loadProducts() : ArrayList<Product>
}