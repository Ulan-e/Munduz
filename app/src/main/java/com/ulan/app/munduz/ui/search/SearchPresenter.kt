package com.ulan.app.munduz.ui.search

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BasePresenter

interface SearchPresenter : BasePresenter {

    fun loadProducts(): MutableList<Product>

}