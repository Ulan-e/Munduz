package com.ulan.app.munduz.developer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ulan.app.munduz.R
import kotlinx.android.synthetic.main.handle_activity.*

class HandleActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.handle_activity)

        setupToolbar()
        callFragment()

    }

    private fun callFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container_frag, ListFragment())
            .commit()
    }

    private fun setupToolbar() {
        setSupportActionBar(handle_toolbar)
        handle_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        handle_toolbar.setNavigationOnClickListener {
            finish()
        }
    }


}