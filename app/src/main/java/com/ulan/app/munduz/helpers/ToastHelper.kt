package com.ulan.app.munduz.helpers

import android.content.Context
import android.widget.Toast

fun showNoProduct(context: Context){
    Toast.makeText(
        context,
        "Такой продукт в данноу время отсутсвует",
        Toast.LENGTH_LONG
    ).show()
}

fun showSuccessMessageSent(context: Context){
    Toast.makeText(
        context,
        "Message Sent",
        Toast.LENGTH_LONG
    ).show()
}
