package com.ulan.app.munduz.listeners

import com.ulan.app.munduz.data.models.SliderImage

interface SliderImagesCallback {

    fun onCallback(value: List<SliderImage>)

}