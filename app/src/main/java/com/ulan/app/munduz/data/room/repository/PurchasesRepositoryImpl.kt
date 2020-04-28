package com.ulan.app.munduz.data.room.repository

import com.ulan.app.munduz.data.models.Picture
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.data.room.dao.PurchasesDao
import com.ulan.app.munduz.developer.Product

class PurchasesRepositoryImpl(private val purchasesDao: PurchasesDao) : PurchasesRepository {

    override fun insert(product: Product) {
        val purchase = generateNewPurchase(product)
        val table = purchasesDao.fetchAllPurchases()
        if (table.isEmpty()) {
            purchasesDao.insertPurchase(purchase)
        } else {
            for (item in table) {
                if (purchase.id != item.id) {
                    purchasesDao.insertPurchase(purchase)
                }
            }
        }
    }

    override fun remove(key: String) {
        val table = purchasesDao.fetchAllPurchases()
        for (item in table) {
            if (key == item.id) {
                purchasesDao.removePurchase(item)
            }
        }
    }

    override fun isExist(key: String): Boolean {
        val table = purchasesDao.fetchAllPurchases()
        for (item in table) {
            if (key == item.id) {
                return true
            }
        }
        return false
    }

    override fun update(purchase: PurchaseEntity) {
        val table = purchasesDao.fetchAllPurchases()
        for (item in table) {
            if (purchase.id == item.id) {
                purchasesDao.updatePurchase(purchase)
            }
        }
    }

    override fun fetchPurchases(): MutableList<PurchaseEntity> {
        return purchasesDao.fetchAllPurchases().toMutableList()
    }

    override fun sumOfPurchases(): Int {
        return purchasesDao.sumOfPurchases()
    }

    override fun removeAll() {
        purchasesDao.removeAllPurchases()
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