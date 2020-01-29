package com.ulan.app.munduz.ui.filtered

interface FilteredPresenter {

    fun setToolbar()
    fun loadProductsByCategory(categoryName: String)
    fun onDetachView()
}