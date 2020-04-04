package com.ulan.app.munduz

import com.ulan.app.munduz.helpers.decCount
import com.ulan.app.munduz.helpers.incCount
import junit.framework.Assert.assertEquals
import org.junit.Test

class CountHelperTest {

    private val ONE = 1
    private val TWO = 2

    @Test
    fun whenIncrementOneReturnTwo(){
        assertEquals(2, incCount(ONE))
    }

    @Test
    fun whenDecrementTwoReturnOne(){
        assertEquals(1, decCount(TWO))
    }

}