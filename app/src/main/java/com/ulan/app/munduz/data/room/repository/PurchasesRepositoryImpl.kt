package com.ulan.app.munduz.data.room.repository

import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.data.room.dao.PurchasesDao
import com.ulan.app.munduz.developer.Product

class PurchasesRepositoryImpl(private val purchasesDao: PurchasesDao) : PurchasesRepository {

    override fun insert(purchase: PurchaseEntity) {
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

    override fun remove(purchase: PurchaseEntity) {
        val table = purchasesDao.fetchAllPurchases()
        for (item in table) {
            if (purchase.id == item.id) {
                purchasesDao.removePurchase(item)
            }
        }
    }

    override fun isExist(purchase: PurchaseEntity): Boolean {
        val table = purchasesDao.fetchAllPurchases()
        for (item in table) {
            if (purchase.id == item.id) {
                return true
            }
        }
        return false
    }

    override fun isExistId(key: String): Boolean {
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

}