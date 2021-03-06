package ulanapp.munduz.ui.fragments.favorite

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.models.FavoriteEntity
import ulanapp.munduz.data.repository.FirebaseRepository
import ulanapp.munduz.data.room.repository.FavoritesRepository
import ulanapp.munduz.interfaces.ProductCallback
import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class FavoritePresenterImpl @Inject constructor(
    firebase: FirebaseRepository,
    favorites: FavoritesRepository
) : BasePresenter<FavoriteView>(), FavoritePresenter {

    private var firebaseRepository: FirebaseRepository = firebase
    private var favoriteRepository: FavoritesRepository = favorites
    private var products = mutableListOf<Product>()

    override fun loadProducts() {
        val keys = favoriteRepository.fetchAll()
        if (keys.size > 0) {
            for (item: FavoriteEntity in keys) {
                firebaseRepository.loadProductByKey(item.key, object : ProductCallback {
                    override fun onCallback(product: Product?) {
                        products.add(product!!)
                        getView()?.showProducts(products)
                    }
                })
            }
        } else {
            getView()?.showEmptyData()
        }
    }
}