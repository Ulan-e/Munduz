package ulanapp.munduz.ui.activities.details

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.ui.base.BaseView

interface DetailsView : BaseView {

    // показываем тулбар
    fun showToolbar()

    // показываем продукт
    fun showProduct(product: Product)

    // изменяем текст корзины
    fun changeBasketText(title: String)

    // добавляем в корзину
    fun addToBasket()

    // отмечаем как понравивщийся
    fun markAsFavorite()

    // удаляем из понравивщийся
    fun markAsNotFavorite()

    // вызодим из фрагмента
    fun closeDetails()

    // переход в корзину
    fun goToBasket()
}