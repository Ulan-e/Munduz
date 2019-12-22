package com.ulan.app.munduz.developer

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ulan.app.munduz.R

class ManagerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manager_activity_layout)

        val buttonManager: Button = findViewById(R.id.manage_products)
        val buttonAdd: Button = findViewById(R.id.add_product)

        buttonAdd.setOnClickListener{
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        buttonManager.setOnClickListener{
            val intent = Intent(this, HandleActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.manager_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}