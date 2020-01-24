package com.ulan.app.munduz.ui.home

interface HomePresenter {

    fun loadProducts()
    fun attachView(view: HomeView)
    fun onDetachView()
}