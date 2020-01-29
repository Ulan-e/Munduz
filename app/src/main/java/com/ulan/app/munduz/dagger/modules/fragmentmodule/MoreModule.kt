package com.ulan.app.munduz.dagger.modules.fragmentmodule

import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.ui.more.MoreFragment
import com.ulan.app.munduz.ui.more.MorePresenter
import com.ulan.app.munduz.ui.more.MorePresenterImpl
import com.ulan.app.munduz.ui.more.MoreView
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