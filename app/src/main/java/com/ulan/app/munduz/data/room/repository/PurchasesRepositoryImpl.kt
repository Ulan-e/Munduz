package com.ulan.app.munduz.data.room.repository

import com.ulan.app.munduz.data.room.dao.PurchasesDao
import com.ulan.app.munduz.data.room.entities.PurchaseEntity

class PurchasesRepositoryImpl(private val purchasesDao: PurchasesDao) : PurchasesRepository {

    override fun insert(purchase: PurchaseEntity) {
        val table = purchasesDao.fetchAllPurchases()
        if (table.isEmpty()) {
            purchasesDao.insertPurchase(purchase)
        } else {
            for (item in table) {
                if (purchase.key != item.key) {
                    purchasesDao.insertPurchase(purchase)
                }
            }
        }
    }

    override fun remove(purchase: PurchaseEntity) {
        val table = purchasesDao.fetchAllPurchases()
        for (item in table) {
            if (purchase.key == item.key) {
                purchasesDao.removePurchase(item)
            }
        }
    }

    override fun isExist(purchase: PurchaseEntity): Boolean {
        val table = purchasesDao.fetchAllPurchases()
        for (item in table) {
            if (purchase.key == item.key) {
                return true
            }
        }
        return false
    }

    override fun fetchPurchases(): ArrayList<PurchaseEntity> {
        return purchasesDao.fetchAllPurchases() as ArrayList<PurchaseEntity>
    }

    override fun sumOfPurchases(): Int {
        return purchasesDao.sumOfPurchases()
    }

    override fun removeAll() {
        purchasesDao.removeAllPurchases()
    }

}