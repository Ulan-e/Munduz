package ulanapp.munduz.ui.fragments.basket

interface BasketPresenter {

    // закгрузка продуктов
    fun loadProducts()

    // клик на покупку
    fun purchaseButtonClicked()

    // переход домой
    fun goToHomeButtonClicked()
}