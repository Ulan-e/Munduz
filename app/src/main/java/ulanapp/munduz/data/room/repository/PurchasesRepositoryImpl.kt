package ulanapp.munduz.data.room.repository

import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.models.Picture
import ulanapp.munduz.data.models.PurchaseEntity
import ulanapp.munduz.data.room.dao.PurchasesDao

class PurchasesRepositoryImpl(private val purchasesDao: PurchasesDao) :
    PurchasesRepository {

    override fun insert(product: Product) {
        val purchase = generateNewPurchase(product)
        val table = purchasesDao.fetchPurchases()
        if (table.isEmpty()) {
            purchasesDao.insert(purchase)
        } else {
            for (item in table) {
                if (purchase.id != item.id) {
                    purchasesDao.insert(purchase)
                }
            }
        }
    }

    override fun remove(key: String) {
        val table = purchasesDao.fetchPurchases()
        for (item in table) {
            if (key == item.id) {
                purchasesDao.remove(item)
            }
        }
    }

    override fun isExist(key: String): Boolean {
        val table = purchasesDao.fetchPurchases()
        for (item in table) {
            if (key == item.id) {
                return true
            }
        }
        return false
    }

    override fun update(purchase: PurchaseEntity) {
        val table = purchasesDao.fetchPurchases()
        for (item in table) {
            if (purchase.id == item.id) {
                purchasesDao.update(purchase)
            }
        }
    }

    override fun fetchAll(): MutableList<PurchaseEntity> {
        return purchasesDao.fetchPurchases().toMutableList()
    }

    override fun purchasesAmount(): Int {
        return purchasesDao.purchasesAmount()
    }

    override fun removeAll() {
        purchasesDao.removePurchases()
    }

    private fun generateNewPurchase(product: Product): PurchaseEntity {
        var purchase = PurchaseEntity()
        var picture = Picture()
        purchase.id = product.id
        purchase.name = product.name
        purchase.category = product.category
        purchase.price = product.cost
        purchase.priceInc = product.cost
        purchase.perPrice = product.priceFor
        purchase.perPriceInc = product.priceFor
        purchase.desc = product.desc
        picture.urlImage = product.picture.urlImage
        picture.urlImage2 = product.picture.urlImage2
        picture.urlImage3 = product.picture.urlImage3
        purchase.picture = picture
        return purchase
    }

}