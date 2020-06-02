package ulanapp.munduz.ui.activities.details

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.ui.base.BaseView

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