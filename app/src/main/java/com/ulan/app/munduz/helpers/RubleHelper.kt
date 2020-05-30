package com.ulan.app.munduz.helpers

import android.os.Build
import android.text.Html
import android.text.Spanned

val RUBLE = " " +  fromHtml(" &#x20bd")

fun fromHtml(source: String?): Spanned? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(source)
    }
}

