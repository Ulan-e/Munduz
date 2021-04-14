package ulanapp.munduz.ui.activities.search

interface SearchPresenter {

    // настройки тулбара
    fun setToolbar()

    // загружаем товары
    fun loadProducts()
}