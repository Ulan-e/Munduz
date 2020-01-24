package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.developer.Product

interface DetailsPresenter {

    fun setProduct(product: Product)
    fun setToolbar()
    fun buyButtonClicked()
    fun favoriteButtonClicked()
    fun onBackPressed()
    fun detachView()

}

