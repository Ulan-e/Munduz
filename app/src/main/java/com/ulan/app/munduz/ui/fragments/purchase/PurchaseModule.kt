package com.ulan.app.munduz.ui.fragments.purchase

import android.content.Context
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.activities.orders.OrdersScope
import dagger.Module
import dagger.Provides

@Module
class PurchaseModule {

    @OrdersScope
    @Provides
    fun purchaseFragment(fragment: PurchaseFragment): PurchaseView {
        return fragment
    }

    @Provides
    fun sendEmail(context: Context): SendEmailHelper {
        return SendEmailHelper(context)
    }

    @OrdersScope
    @Provides
    fun purchasePresenter(view: PurchaseView): PurchasePresenter {
        return PurchasePresenterImpl(view)
    }

}