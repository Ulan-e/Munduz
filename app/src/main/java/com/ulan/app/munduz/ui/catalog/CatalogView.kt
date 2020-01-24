package com.ulan.app.munduz.ui.catalog

interface CatalogView {


    fun showCatalog(catalogs : MutableList<String>)
    fun showNoCatalog(text: String)
}