package ulanapp.munduz.ui.activities.details

import ulanapp.munduz.data.models.Product

interface DetailsPresenter {

    // настройки тулбара
    fun setToolbar()

    // ставим продукт
    fun setProduct(product: Product)

    // понравивщийся?
    fun isFavorite()

    // в корзине?
    fun isInBasket()

    // клик на добавление в понравивщийся
    fun favoriteClicked()

    // клик на удаление из понравивщийся
    fun unfavoriteClicked()

    // клик на добавление в корзину
    fun addToBasketClicked()

    // назад
    fun onBackPressed()
}