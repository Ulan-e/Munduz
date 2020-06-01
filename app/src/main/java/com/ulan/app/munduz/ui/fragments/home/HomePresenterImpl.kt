package com.ulan.app.munduz.ui.fragments.home

import com.ulan.app.munduz.data.models.SliderImage
import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.interfaces.ProductsCallback
import com.ulan.app.munduz.interfaces.SliderImagesCallback
import javax.inject.Inject

class HomePresenterImpl : HomePresenter {

    private var view: HomeView? = null
    private var firebaseRepository: FirebaseRepository
    private var favoritesRepository: FavoritesRepository

    @Inject
    constructor(view: HomeView, firebase: FirebaseRepository, favorites: FavoritesRepository) {
        this.view = view
        this.firebaseRepository = firebase
        this.favoritesRepository = favorites
    }

    override fun loadProducts() {
            firebaseRepository.loadProductsByRecommendation(object : ProductsCallback {
                override fun onCallback(values: MutableList<Product>) {
                    if (values.size > 0) {
                        view?.showProducts(values)
                    } else {
                        view?.showEmptyData()
                    }
                }
            })
    }

    override fun loadSliderImages() {
            firebaseRepository.loadSliderPhotos(object : SliderImagesCallback {
                override fun onCallback(value: List<SliderImage>) {
                    view?.showSliderImages(value)
                }
            })
    }

    override fun detachView() {
        view = null
    }

}