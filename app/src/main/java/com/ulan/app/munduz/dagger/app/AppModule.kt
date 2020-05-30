package com.ulan.app.munduz.dagger.app

import android.content.Context
import com.ulan.app.munduz.BaseApplication
import dagger.Binds
import dagger.Module
import javax.inject.Scope

@Module
abstract class AppModule {

    @Binds
    abstract fun context(application: BaseApplication): Context

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope