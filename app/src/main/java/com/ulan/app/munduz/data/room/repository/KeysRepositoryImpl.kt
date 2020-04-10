package com.ulan.app.munduz.data.room.repository

import com.ulan.app.munduz.data.room.dao.KeysDao
import com.ulan.app.munduz.data.room.entities.KeyEntity

class KeysRepositoryImpl(private val daoKeys: KeysDao) : BaseRepository {

    override fun insert(key: String) {
        val table = daoKeys.fetchAllKeys()
        if (table.isEmpty()) {
            daoKeys.insertKey(getProduct(key))
        } else {
            for (item in table) {
                if (key != item.key) {
                    daoKeys.insertKey(getProduct(key))
                }
            }
        }
    }

    override fun remove(key: String) {
        val table = daoKeys.fetchAllKeys()
        for (item in table) {
            if (key == item.key) {
                daoKeys.removeKey(item)
            }
        }
    }

    override fun isExist(key: String): Boolean {
        val table = daoKeys.fetchAllKeys()
        for (item in table) {
            if (key == item.key) {
                return true
            }
        }
        return false
    }

    override fun removeAll() {
        daoKeys.removeAllKeys()
    }
    private fun getProduct(productKey: String): KeyEntity {
        val keyEntity = KeyEntity()
        keyEntity.key = productKey

        return keyEntity
    }

    fun fetchAllKeys(): List<KeyEntity> {
        return daoKeys.fetchAllKeys()
    }
}