package ulanapp.munduz.interfaces

import ulanapp.munduz.data.models.Product

interface ProductsCallback {

    fun onCallback(values: MutableList<Product>)

}