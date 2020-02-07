package com.ulan.app.munduz.ui.liked

import com.ulan.app.munduz.ui.base.BasePresenter

interface LikedPresenter : BasePresenter{
    
    fun loadProducts()
    fun deleteButtonClicked()
}