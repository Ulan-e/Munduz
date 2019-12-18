package com.ulan.app.munduz.developer

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.ulan.app.munduz.R

class HandleProductsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.handle_products_layout)

        val listView:ListView = findViewById(R.id.handle_list_view)
    }
}