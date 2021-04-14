package ulanapp.munduz.interfaces

import ulanapp.munduz.data.models.Product

interface OnItemClickListener {

    fun onItemClick(product: Product)
}