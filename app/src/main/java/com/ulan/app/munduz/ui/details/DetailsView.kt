package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface DetailsView : BaseView{

    fun showProduct(product: Product)
    fun addToBasket()
    fun markAsLiked()
    fun markAsNotLiked()
    fun closeDetails()
    fun showSnackBar(result: String)

}