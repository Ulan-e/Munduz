package com.ulan.app.munduz.ui.activities.details

import com.ulan.app.munduz.developer.Product

interface DetailsPresenter {

    fun setToolbar()

    fun setProduct(product: Product)

    fun isFavorite()

    fun isInBasket()

    fun favoriteClicked()

    fun unfavoriteClicked()

    fun addToBasketClicked()

    fun onBackPressed()

}

