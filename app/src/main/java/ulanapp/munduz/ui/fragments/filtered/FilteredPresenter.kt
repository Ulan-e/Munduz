package ulanapp.munduz.ui.fragments.filtered

interface FilteredPresenter {

    // загрузить продукты по наименованию
    fun loadProductsByCategory(categoryName: String)
}