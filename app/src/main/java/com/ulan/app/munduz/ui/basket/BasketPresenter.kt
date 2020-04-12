package com.ulan.app.munduz.ui.basket

import com.ulan.app.munduz.ui.base.BasePresenter

interface BasketPresenter : BasePresenter {

    fun loadProducts()
    fun purchaseButtonClicked()
    fun goToHomeButtonClicked()
    fun purchasesAmountChanged()
}