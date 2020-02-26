package com.ulan.app.munduz.ui.home

import android.util.Log
import com.ulan.app.munduz.data.model.SliderImage
import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.data.roomdatabase.RoomRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductListCallback
import com.ulan.app.munduz.listeners.SliderImagesCallback
import javax.inject.Inject

class HomePresenterImpl : HomePresenter {

    private var mRepository: Repository
    private var mRoomRepository: RoomRepository
    private var mView: HomeView? = null

    @Inject
    constructor(view: HomeView, mRepository: Repository, roomRepository: RoomRepository) {
        this.mView = view
        this.mRepository = mRepository
        this.mRoomRepository = roomRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProducts() {
            mRepository.loadNewProducts(object : ProductListCallback {
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