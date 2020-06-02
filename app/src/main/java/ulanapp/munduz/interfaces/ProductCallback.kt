package ulanapp.munduz.interfaces

import ulanapp.munduz.data.models.Product

interface ProductCallback{

    fun onCallback(product: Product)
}