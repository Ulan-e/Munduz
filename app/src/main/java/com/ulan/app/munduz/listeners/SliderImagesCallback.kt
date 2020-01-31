package com.ulan.app.munduz.listeners

import com.ulan.app.munduz.data.model.SliderImage

interface SliderImagesCallback {
    fun onCallback(value: ArrayList<SliderImage>)
}