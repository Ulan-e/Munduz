package com.ulan.app.munduz.ui.activities.details

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BasePresenter

interface DetailsPresenter : BasePresenter {

    fun setToolbar()

    fun setProduct(product: Product)

    fun isFavoriteProduct()

    fun isInAlreadyInBasket()

    fun favoriteClicked()

    fun unFavoriteClicked()

    fun addToBasketClicked()

    fun onBackPressed()

}

