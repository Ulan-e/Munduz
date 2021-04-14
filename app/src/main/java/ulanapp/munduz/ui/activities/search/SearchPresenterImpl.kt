package ulanapp.munduz.ui.activities.search

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.repository.FirebaseRepository
import ulanapp.munduz.interfaces.ProductsCallback
import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class SearchPresenterImpl @Inject constructor(
    private var repository: FirebaseRepository
) : BasePresenter<SearchView>(), SearchPresenter {

    private var products = mutableListOf<Product>()

    override fun setToolbar() {
        getView()?.showToolbar()
    }

    override fun loadProducts() {
        repository.loadProducts(object : ProductsCallback {
            override fun onCallback(values: MutableList<Product>) {
                if (values.isNotEmpty()) {
                    getView()?.showProducts(values)
                } else {
                    getView()?.showEmptyData()
                }
            }
        })
    }
}