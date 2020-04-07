package com.ulan.app.munduz.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.ulan.app.munduz.R
import com.ulan.app.munduz.ui.main.MainActivity
import kotlinx.android.synthetic.main.splash_screen.*

class SplashActivity : AppCompatActivity(){

    private var mDelayHandler: Handler? = null
    private var SPLASHDELAY: Long = 2500

    private val mRunnable: Runnable = Runnable {
        if(!isFinishing){
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

        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASHDELAY)
    }

    override fun onDestroy() {
        if(mDelayHandler != null){
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }

}