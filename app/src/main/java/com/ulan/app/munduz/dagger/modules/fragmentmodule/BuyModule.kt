package com.ulan.app.munduz.dagger.modules.fragmentmodule

import android.content.Context
import com.ulan.app.munduz.dagger.scopes.DetailsScope
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.buy.BuyFragment
import com.ulan.app.munduz.ui.buy.BuyPresenter
import com.ulan.app.munduz.ui.buy.BuyPresenterImpl
import com.ulan.app.munduz.ui.buy.BuyView
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class BuyModule {

    @DetailsScope
    @Provides
    fun buyFragment(buyFragment: BuyFragment) : BuyView{
        return buyFragment
    }

    @DetailsScope
    @Provides
    fun buyPresenter(buyView: BuyView, repository: Repository): BuyPresenter{
        return  BuyPresenterImpl(buyView, repository)
    }

    @Provides
    fun sendEmail(context: Context) : SendEmailHelper {
        return SendEmailHelper(context)
    }

}