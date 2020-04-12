package com.ulan.app.munduz.ui.home

import com.ulan.app.munduz.data.models.SliderImage
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.data.room.repository.FavoritesRepositoryImpl
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductsCallback
import com.ulan.app.munduz.listeners.SliderImagesCallback
import javax.inject.Inject

class HomePresenterImpl : HomePresenter {

    private var mRepository: FirebaseRepository
    private var mRoomRepository: FavoritesRepository
    private var mView: HomeView? = null

    @Inject
    constructor(view: HomeView, mRepository: FirebaseRepository, roomRepository: FavoritesRepository) {
        this.mView = view
        this.mRepository = mRepository
        this.mRoomRepository = roomRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProducts() {
            mRepository.loadNewProducts(object : ProductsCallback {
                override fun onCallback(values: MutableList<Product>) {
                    if (values.size > 0) {
                        mView?.showProducts(values)
                    } else {
                        mView?.showEmptyData()
                    }
                }
            })
    }

    override fun loadSliderImages() {
            mRepository.loadSliderPhotos(object : SliderImagesCallback {
                override fun onCallback(value: ArrayList<SliderImage>) {
                    mView?.showSliderImages(value)
                }
            })
    }

    override fun detachView() {
        mView = null
    }

}