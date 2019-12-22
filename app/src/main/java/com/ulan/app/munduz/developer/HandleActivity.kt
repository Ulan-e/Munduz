package com.ulan.app.munduz.developer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ulan.app.munduz.R
import kotlinx.android.synthetic.main.handle_activity.*

class HandleActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.handle_activity)

        setSupportActionBar(handle_toolbar)
        val actionBar = supportActionBar
        actionBar!!.setTitle("Handle Product")

        supportFragmentManager.beginTransaction().add(R.id.container_frag, HandleFragment()).commit()
    }


}