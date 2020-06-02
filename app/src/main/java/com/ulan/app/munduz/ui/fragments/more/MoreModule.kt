package com.ulan.app.munduz.ui.fragments.more

import com.ulan.app.munduz.ui.activities.main.MainScope
import dagger.Binds
import dagger.Module

@Module
abstract class MoreModule {

    @MainScope
    @Binds
    abstract fun moreView(moreFragment: MoreFragment): MoreView

}