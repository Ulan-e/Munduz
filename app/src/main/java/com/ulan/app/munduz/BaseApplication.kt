package com.ulan.app.munduz

import com.ulan.app.munduz.dagger.component.AppComponent
import com.ulan.app.munduz.dagger.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication(){

    private lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}