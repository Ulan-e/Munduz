package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BasePresenter

interface DetailsPresenter: BasePresenter{

    fun setProduct(product: Product)
    fun buyButtonClicked()
    fun isFavoriteProduct()
    fun favoriteButtonClicked()
    fun onBackPressed()

}

