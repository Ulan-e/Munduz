package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.developer.Product

interface DetailsView{

    fun initToolbar(title: String)
    fun closeDetails()
    fun showProduct(product: Product)
    fun showNoProduct(text: String)

}