package ulanapp.munduz.ui.fragments.home

import ulanapp.munduz.data.repository.FirebaseRepository

interface HomePresenter {

    // ставим репозиторий
    fun setRepository(repository: FirebaseRepository)

    // загружаем проудкты
    fun loadProducts()

    // загружаем изображения слайдера
    fun loadSliderImages()
}