package ulanapp.munduz.ui.activities.main

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