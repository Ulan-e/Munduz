package com.ulan.app.munduz.ui.more

import com.ulan.app.munduz.ui.main.MainScope
import dagger.Binds
import dagger.Module

@Module
abstract class MoreModule {

    @MainScope
    @Binds
    abstract fun moreView(moreFragment: MoreFragment): MoreView

    @MainScope
    @Binds
    abstract fun morePresenter(morePresenter: MorePresenterImpl): MorePresenter
}