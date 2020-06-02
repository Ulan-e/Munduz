package com.ulan.app.munduz.ui.fragments.home

import android.util.Log
import com.ulan.app.munduz.data.models.SliderImage
import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.interfaces.ProductsCallback
import com.ulan.app.munduz.interfaces.SliderImagesCallback
import com.ulan.app.munduz.ui.base.BasePresenter
import javax.inject.Inject

class HomePresenterImpl : BasePresenter<HomeView>, HomePresenter {

    private var firebaseRepository: FirebaseRepository
    private var favoritesRepository: FavoritesRepository

    @Inject
    constructor(firebase: FirebaseRepository, favorites: FavoritesRepository) {
        this.firebaseRepository = firebase
        this.favoritesRepository = favorites
    }

    override fun bindView(view: HomeView) {
        super.bindView(view)
        Log.d("ulanbek", "HomePresenterImpl bindView() $view")
    }

    override fun unbindView(view: HomeView) {
        super.unbindView(view)
        Log.d("ulanbek", "HomePresenterImpl unbindView() $view")
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