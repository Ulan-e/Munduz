package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BasePresenter

interface DetailsPresenter: BasePresenter{

    fun setProduct(product: Product)
    fun isFavoriteProduct()
    fun favoriteClicked()
    fun unFavoriteClicked()
    fun addToBasketClicked()
    fun onBackPressed()

}

