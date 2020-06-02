package com.ulan.app.munduz.ui.fragments.basket

interface BasketPresenter {

    fun loadProducts()

    fun purchaseButtonClicked()

    fun goToHomeButtonClicked()

    fun purchasesAmountChanged()

}