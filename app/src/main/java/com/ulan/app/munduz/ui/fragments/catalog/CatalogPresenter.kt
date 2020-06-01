package com.ulan.app.munduz.ui.fragments.catalog

import com.ulan.app.munduz.ui.base.BasePresenter

interface CatalogPresenter : BasePresenter {

    fun setCatalog(catalog: MutableList<String>)

}