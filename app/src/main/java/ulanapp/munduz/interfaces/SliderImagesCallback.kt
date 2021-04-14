package ulanapp.munduz.interfaces

import ulanapp.munduz.data.models.SliderImage

interface SliderImagesCallback {

    fun onCallback(value: List<SliderImage>)
}