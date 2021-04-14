package ulanapp.munduz.ui.base

import android.os.Bundle
import android.widget.Toast
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import ulanapp.munduz.R
import ulanapp.munduz.helpers.isNetworkAvailable

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    protected fun checkInternetConnection(){
        if(!isNetworkAvailable(this)){
            Toast.makeText(
                this, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT
            ).show()
        }
    }
}