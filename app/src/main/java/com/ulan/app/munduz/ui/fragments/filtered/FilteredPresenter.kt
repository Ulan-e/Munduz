package com.ulan.app.munduz.ui.fragments.filtered

import com.ulan.app.munduz.ui.base.BasePresenter

interface FilteredPresenter: BasePresenter {

    fun loadProductsByCategory(categoryName: String)

}