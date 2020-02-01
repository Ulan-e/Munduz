package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.developer.Product

interface DetailsView{

    fun initToolbar()
    fun showProduct(product: Product)
    fun showNoProduct()
    fun showOrderProduct()
    fun markAsLiked()
    fun closeDetails()

}