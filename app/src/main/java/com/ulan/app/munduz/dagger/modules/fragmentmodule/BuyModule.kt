package com.ulan.app.munduz.dagger.modules.fragmentmodule

import com.ulan.app.munduz.dagger.scopes.DetailsScope
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.ui.buy.BuyFragment
import com.ulan.app.munduz.ui.buy.BuyPresenter
import com.ulan.app.munduz.ui.buy.BuyPresenterImpl
import com.ulan.app.munduz.ui.buy.BuyView
import dagger.Binds
import dagger.Module

@Module
abstract class BuyModule {

    @DetailsScope
    @Binds
    abstract fun buyFragment(buyFragment: BuyFragment) : BuyView

    @DetailsScope
    @Binds
    abstract fun buyPresenter(buyPresenter: BuyPresenterImpl): BuyPresenter

}