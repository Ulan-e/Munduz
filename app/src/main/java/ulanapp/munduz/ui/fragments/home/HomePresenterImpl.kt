package ulanapp.munduz.ui.fragments.home

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.models.SliderImage
import ulanapp.munduz.data.repository.FirebaseRepository
import ulanapp.munduz.data.room.repository.FavoritesRepository
import ulanapp.munduz.interfaces.ProductsCallback
import ulanapp.munduz.interfaces.SliderImagesCallback
import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class HomePresenterImpl : BasePresenter<HomeView>, HomePresenter {

    private var firebaseRepository: FirebaseRepository
    private var favoritesRepository: FavoritesRepository

    @Inject
    constructor(firebase: FirebaseRepository, favorites: FavoritesRepository) {
        this.firebaseRepository = firebase
        this.favoritesRepository = favorites
    }

    override fun loadProducts() {
            firebaseRepository.loadProductsByRecommendation(object : ProductsCallback {
                override fun onCallback(values: MutableList<Product>) {
                    if (values.size > 0) {
                        getView()?.showProducts(values)
                    } else {
                        getView()?.showEmptyData()
                    }
                }
            })
    }

    override fun loadSliderImages() {
            firebaseRepository.loadSliderPhotos(object : SliderImagesCallback {
                override fun onCallback(value: List<SliderImage>) {
                    getView()?.showSliderImages(value)
                }
            })
    }

}