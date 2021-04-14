package ulanapp.munduz.ui.activities.details

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.room.repository.FavoritesRepository
import ulanapp.munduz.data.room.repository.PurchasesRepository
import ulanapp.munduz.helpers.Constants.Companion.ALREADY_IN_BASKET
import ulanapp.munduz.helpers.Constants.Companion.NOT_IN_BASKET
import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class DetailsPresenterImpl @Inject constructor(
    repository: PurchasesRepository,
    keysRepository: FavoritesRepository
) : BasePresenter<DetailsView>(), DetailsPresenter {

    private lateinit var product: Product
    private var purchaseRepository: PurchasesRepository = repository
    private var favoriteRepository: FavoritesRepository = keysRepository

    override fun setProduct(product: Product) {
        this.product = product
        getView()?.showProduct(this.product)
        getView()?.changeBasketText(getBasketText())
    }

    private fun getBasketText(): String {
        return if (purchaseRepository.isExist(product.id)) {
            NOT_IN_BASKET
        } else {
            NOT_IN_BASKET
        }
    }

    override fun setToolbar() {
        getView()?.showToolbar()
    }

    override fun isFavorite() {
        if (favoriteRepository.isExist(product.id)) {
            getView()?.markAsFavorite()
        }
    }

    override fun isInBasket() {
        if (purchaseRepository.isExist(product.id)) {
            getView()?.changeBasketText(ALREADY_IN_BASKET)
        } else {
            getView()?.changeBasketText(NOT_IN_BASKET)
        }
    }

    override fun favoriteClicked() {
        if (favoriteRepository.isExist(product.id)) {
            favoriteRepository.remove(product.id)
            getView()?.markAsNotFavorite()
        } else {
            favoriteRepository.insert(product.id)
            getView()?.markAsFavorite()
        }
    }

    override fun unfavoriteClicked() {
        favoriteRepository.remove(product.id)
    }

    override fun onBackPressed() {
        getView()?.closeDetails()
    }

    override fun addToBasketClicked() {
        if (purchaseRepository.isExist(product.id)) {
            getView()?.goToBasket()
        } else {
            purchaseRepository.insert(product)
            getView()?.changeBasketText(ALREADY_IN_BASKET)
        }
    }
}