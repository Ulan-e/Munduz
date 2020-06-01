package com.ulan.app.munduz.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.ulan.app.munduz.R
import com.ulan.app.munduz.ui.activities.main.MainActivity
import kotlinx.android.synthetic.main.splash_screen.*

class SplashActivity : AppCompatActivity() {

    private var delayHandler: Handler? = null
    private var SPLASH_DELAY: Long = 2300

    private val runnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val typeface = ResourcesCompat.getFont(this, R.font.forte)
        splash_text.text = resources.getString(R.string.app_name)
        splash_text.typeface = typeface

        delayHandler = Handler()
        delayHandler!!.postDelayed(runnable, SPLASH_DELAY)
    }

    override fun onDestroy() {
        if (delayHandler != null) {
            delayHandler!!.removeCallbacks(runnable)
        }
        super.onDestroy()
    }

}