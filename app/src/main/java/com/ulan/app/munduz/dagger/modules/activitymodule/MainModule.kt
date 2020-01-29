package com.ulan.app.munduz.dagger.modules.activitymodule

import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.ui.main.MainActivity
import com.ulan.app.munduz.ui.main.MainPresenter
import com.ulan.app.munduz.ui.main.MainPresenterImpl
import com.ulan.app.munduz.ui.main.MainView
import dagger.Binds
import dagger.Module


@Module
abstract class MainModule {

    @MainScope
    @Binds
    abstract fun mainView(mainActivity: MainActivity): MainView

    @MainScope
    @Binds
    abstract fun mainPresenter(mainPresenter: MainPresenterImpl): MainPresenter

}