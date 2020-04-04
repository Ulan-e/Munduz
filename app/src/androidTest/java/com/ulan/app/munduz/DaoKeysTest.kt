package com.ulan.app.munduz

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.ulan.app.munduz.data.roomdatabase.DaoKeys
import com.ulan.app.munduz.data.roomdatabase.KeyEntity
import com.ulan.app.munduz.data.roomdatabase.StarredDatabase
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class DaoKeysTest {

    private lateinit var database: StarredDatabase
    private lateinit var daoKeys: DaoKeys
    private val keyEntity1 = KeyEntity(1, "Key1")
    private val keyEntity2 = KeyEntity(2, "Key2")

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            StarredDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        daoKeys = database.keysDao()
    }

    @Test
    fun whenInsertReturnSameSize() {
        daoKeys.insertKey(keyEntity1)
        assertEquals(1, daoKeys.fetchAllKeys().size)
    }

    @Test
    fun whenInsertReturnSameKey() {
        daoKeys.insertKey(keyEntity1)
        assertEquals(keyEntity1.id, daoKeys.fetchAllKeys().get(0).id)
        assertEquals(keyEntity1.key, daoKeys.fetchAllKeys().get(0).key)
    }

    @Test
    fun whenInsertReturnErrorEmptyKey() {
        val id = 0
        val key = ""
        val keyEntity = KeyEntity(id, key)
        daoKeys.insertKey(keyEntity)
        fail("Wrong key")
    }

    @Test
    fun whenFetchAllReturnSameSize() {
        daoKeys.insertKey(keyEntity1)
        daoKeys.insertKey(keyEntity2)
        assertEquals(2, daoKeys.fetchAllKeys().size)
    }

    @Test
    fun whenFetchAllReturnSameKey() {
        daoKeys.insertKey(keyEntity1)
        daoKeys.insertKey(keyEntity2)
        assertEquals(keyEntity2.key, daoKeys.fetchAllKeys().get(1).key)
    }

    @Test
    fun whenFetchAllReturnEmpty(){
        assertEquals(0, daoKeys.fetchAllKeys().size)
    }

    @Test
    fun whenRemoveReturnSuccess(){
        daoKeys.insertKey(keyEntity1)
        daoKeys.removeKey(keyEntity1)
        for(k: KeyEntity in daoKeys.fetchAllKeys()){
            assertNotSame(k.key, keyEntity1.key)
        }
    }

    @Test
    fun whenRemoveAllReturnEmpty(){
        daoKeys.insertKey(keyEntity1)
        daoKeys.insertKey(keyEntity2)
        daoKeys.removeAllKeys()
        assertEquals(0, daoKeys.fetchAllKeys().size)
    }

    @After
    fun tearDown() {
        database.close()
    }
}