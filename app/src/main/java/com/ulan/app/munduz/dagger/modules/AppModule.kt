package com.ulan.app.munduz.dagger.modules

import android.content.Context
import com.ulan.app.munduz.BaseApplication
import dagger.Binds
import dagger.Module


@Module
abstract class AppModule {

    @Binds
    abstract fun context(application: BaseApplication): Context


}