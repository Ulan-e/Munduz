package ulanapp.munduz

import com.google.firebase.database.FirebaseDatabase
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ulanapp.munduz.dagger.DaggerAppComponent

class BaseApplication : DaggerApplication(){

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }


}