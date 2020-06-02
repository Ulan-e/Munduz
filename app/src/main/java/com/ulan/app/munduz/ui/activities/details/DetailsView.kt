package com.ulan.app.munduz.ui.activities.details

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface DetailsView : BaseView {

    fun showToolbar()

    fun showProduct(product: Product)

    fun changeBasketText(title: String)

    fun addToBasket()

    fun markAsFavorite()

    fun markAsNotFavorite()

    fun closeDetails()

    fun goToBasket()

}