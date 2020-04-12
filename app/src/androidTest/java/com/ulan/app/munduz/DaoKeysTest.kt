package com.ulan.app.munduz

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.ulan.app.munduz.data.room.dao.FavoritesDao
import com.ulan.app.munduz.data.models.FavoriteEntity
import com.ulan.app.munduz.data.room.StarredDatabase
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class DaoKeysTest {

    private lateinit var database: StarredDatabase
    private lateinit var daoKeys: FavoritesDao
    private val keyEntity1 =
        FavoriteEntity(1, "Key1")
    private val keyEntity2 =
        FavoriteEntity(2, "Key2")

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
        daoKeys.insert(keyEntity1)
        assertEquals(1, daoKeys.fetchFavorites().size)
    }

    @Test
    fun whenInsertReturnSameKey() {
        daoKeys.insert(keyEntity1)
        assertEquals(keyEntity1.id, daoKeys.fetchFavorites().get(0).id)
        assertEquals(keyEntity1.key, daoKeys.fetchFavorites().get(0).key)
    }

    @Test
    fun whenInsertReturnErrorEmptyKey() {
        val id = 0
        val key = ""
        val keyEntity =
            FavoriteEntity(id, key)
        daoKeys.insert(keyEntity)
        fail("Wrong key")
    }

    @Test
    fun whenFetchAllReturnSameSize() {
        daoKeys.insert(keyEntity1)
        daoKeys.insert(keyEntity2)
        assertEquals(2, daoKeys.fetchFavorites().size)
    }

    @Test
    fun whenFetchAllReturnSameKey() {
        daoKeys.insert(keyEntity1)
        daoKeys.insert(keyEntity2)
        assertEquals(keyEntity2.key, daoKeys.fetchFavorites().get(1).key)
    }

    @Test
    fun whenFetchAllReturnEmpty(){
        assertEquals(0, daoKeys.fetchFavorites().size)
    }

    @Test
    fun whenRemoveReturnSuccess(){
        daoKeys.insert(keyEntity1)
        daoKeys.remove(keyEntity1)
        for(k: FavoriteEntity in daoKeys.fetchFavorites()){
            assertNotSame(k.key, keyEntity1.key)
        }
    }

    @Test
    fun whenRemoveAllReturnEmpty(){
        daoKeys.insert(keyEntity1)
        daoKeys.insert(keyEntity2)
        daoKeys.removeFavorites()
        assertEquals(0, daoKeys.fetchFavorites().size)
    }

    @After
    fun tearDown() {
        database.close()
    }
}