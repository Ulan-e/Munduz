package ulanapp.munduz.ui.activities.details

import ulanapp.munduz.data.models.Product


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

