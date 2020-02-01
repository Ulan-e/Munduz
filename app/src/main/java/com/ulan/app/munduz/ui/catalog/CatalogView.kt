package com.ulan.app.munduz.ui.catalog

interface CatalogView {

    fun showToolbar()
    fun showCatalog(catalog: MutableList<String>)
    fun showNoCatalog()
}