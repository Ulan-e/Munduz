package ulanapp.munduz.ui.fragments.filtered

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.repository.FirebaseRepository
import ulanapp.munduz.interfaces.ProductsCallback
import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class FilteredPresenterImpl @Inject constructor(
    repository: FirebaseRepository
) : BasePresenter<FilteredView>(), FilteredPresenter {

    private var repository: FirebaseRepository? = repository

    override fun loadProductsByCategory(categoryName: String) {
        repository?.loadProductsByCategory(categoryName, object : ProductsCallback {
            override fun onCallback(values: MutableList<Product>) {
                if (values.size > 0) {
                    getView()?.showProducts(values)
                } else {
                    getView()?.showEmptyData()
                }
            }
        })
    }
}