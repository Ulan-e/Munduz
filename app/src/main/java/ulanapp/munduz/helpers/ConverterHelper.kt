package ulanapp.munduz.helpers

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

// конвертируем Long в время
fun convertLongToTime(time: Long): String{
    var date = Date(time)
    var timeFormat = SimpleDateFormat("yyyy.MM.dd  HH:mm")
    return timeFormat.format(date)
}

// конвертируем строку в число
fun convertStringToInt(weight: String): Int {
    var result = 0
    val p: Pattern = Pattern.compile("\\d+")
    val m: Matcher = p.matcher(weight)
    while (m.find()) {
        result = m.group().toInt()
    }
    return result
}

// конвертируем число в строку
fun convertIntToString(weight: String): String {
    return weight.replace(Regex("\\d+"), "")
}