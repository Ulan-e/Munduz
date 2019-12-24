package com.ulan.app.munduz.developer

import java.text.SimpleDateFormat
import java.util.*

fun Long.convertLongToTime(time: Long): String{
    var date = Date(time)
    var timeFormat = SimpleDateFormat("yyyy.MM.dd  HH:mm")
    return timeFormat.format(date)
}