package com.ulan.app.munduz.helpers

fun incCount(count: Int): Int {
    return count.inc()
}

fun decCount(count: Int): Int {
    if (count == 1) {
        return 1
    }
    return count.dec()
}