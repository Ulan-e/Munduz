package com.ulan.app.munduz.helpers

import android.content.Context
import android.widget.Toast

fun showNoProduct(context: Context){
    Toast.makeText(
        context,
        "Такой продукт в данное время отсутсвует",
        Toast.LENGTH_LONG
    ).show()
}

fun showSuccessMessageSent(context: Context){
    Toast.makeText(
        context,
        "Спасибо за ваш отзыв",
        Toast.LENGTH_LONG
    ).show()
}

fun showEmptyFields(context: Context){
    Toast.makeText(
        context,
        "Заполните поля",
        Toast.LENGTH_LONG
    ).show()
}
