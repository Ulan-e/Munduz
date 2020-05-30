package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface DetailsView : BaseView {

    fun showProduct(product: Product)

    fun changeAddToBasketText(title: String)

    fun addToBasket()

    fun markAsFavorite()

    fun markAsNotFavorite()

    fun closeDetails()

    fun goToBasket()

}