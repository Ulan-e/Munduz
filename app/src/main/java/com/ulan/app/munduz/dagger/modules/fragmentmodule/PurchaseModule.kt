package com.ulan.app.munduz.dagger.modules.fragmentmodule

import android.content.Context
import com.ulan.app.munduz.dagger.scopes.DetailsScope
import com.ulan.app.munduz.dagger.scopes.OrdersScope
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.orders.OrdersActivity
import com.ulan.app.munduz.ui.orders.OrdersPresenter
import com.ulan.app.munduz.ui.orders.OrdersPresenterImpl
import com.ulan.app.munduz.ui.orders.OrdersView
import com.ulan.app.munduz.ui.purchase.PurchaseFragment
import com.ulan.app.munduz.ui.purchase.PurchasePresenter
import com.ulan.app.munduz.ui.purchase.PurchasePresenterImpl
import com.ulan.app.munduz.ui.purchase.PurchaseView
import dagger.Module
import dagger.Provides

@Module
class PurchaseModule {

    @OrdersScope
    @Provides
    fun purchaseFragment(fragment: PurchaseFragment) : PurchaseView {
        return fragment
    }

    @Provides
    fun sendEmail(context: Context) : SendEmailHelper {
        return SendEmailHelper(context)
    }

    @OrdersScope
    @Provides
    fun purchasePresenter(view: PurchaseView): PurchasePresenter {
        return PurchasePresenterImpl(view)
    }

}