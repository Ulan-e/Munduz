package ulanapp.munduz.ui.fragments.home

import ulanapp.munduz.data.repository.FirebaseRepository

interface HomePresenter {

    fun setRepository(repository: FirebaseRepository)

    fun loadProducts()

    fun loadSliderImages()

}