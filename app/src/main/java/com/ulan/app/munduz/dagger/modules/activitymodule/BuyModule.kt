package com.ulan.app.munduz.dagger.modules.activitymodule

import android.content.Context
import com.ulan.app.munduz.dagger.scopes.DetailsScope
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.buy.BuyActivity
import com.ulan.app.munduz.ui.buy.BuyPresenter
import com.ulan.app.munduz.ui.buy.BuyPresenterImpl
import com.ulan.app.munduz.ui.buy.BuyView
import dagger.Module
import dagger.Provides

@Module
class BuyModule {

    @DetailsScope
    @Provides
    fun buyFragment(buyActivity: BuyActivity) : BuyView{
        return buyActivity
    }

    @DetailsScope
    @Provides
    fun buyPresenter(buyView: BuyView, repository: FirebaseRepository): BuyPresenter{
        return BuyPresenterImpl(buyView, repository)
    }

    @Provides
    fun sendEmail(context: Context) : SendEmailHelper {
        return SendEmailHelper(context)
    }

}