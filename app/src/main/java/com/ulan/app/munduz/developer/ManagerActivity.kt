package com.ulan.app.munduz.developer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.orders.DeveloperMessage

class ManagerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manager_activity_layout)

        setupToolbar()
        callAddActivity()
        callHandleActivity()
        callDeveloperMessage()

    }

    private fun callDeveloperMessage() {
        val buttonSend = findViewById<Button>(R.id.message_to_developer)
        buttonSend.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            val messageFragment = DeveloperMessage()
            messageFragment.show(ft, "dialog")
        }
    }

    private fun callHandleActivity() {
        val buttonManager = findViewById<Button>(R.id.manage_products)
        buttonManager.setOnClickListener{
            val intent = Intent(this, HandleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun callAddActivity() {
        val buttonAdd = findViewById<Button>(R.id.add_product)
        buttonAdd.setOnClickListener{
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.manage_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Менеджер"
        toolbar?.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar?.setNavigationOnClickListener {
            finish()
        }
    }
}