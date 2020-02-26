package com.ulan.app.munduz.data.roomdatabase

class RoomRepositoryImpl(private val daoKeys: DaoKeys) : RoomRepository {

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

    override fun isKeyExists(key: String): Boolean {
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

    override fun isLiked(key: String): Boolean {
        val table = daoKeys.fetchAllKeys()
        for (item in table) {
            if (key == item.key) {
                return true
            }
        }
        return false
    }

    private fun getProduct(productKey: String): KeyEntity {
        val keyEntity = KeyEntity()
        keyEntity.key = productKey
        return keyEntity
    }
}