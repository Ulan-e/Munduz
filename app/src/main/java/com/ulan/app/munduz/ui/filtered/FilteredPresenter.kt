package com.ulan.app.munduz.ui.filtered

interface FilteredPresenter {

    fun loadProducts(categoryName: String)
    fun onDetachView()
}