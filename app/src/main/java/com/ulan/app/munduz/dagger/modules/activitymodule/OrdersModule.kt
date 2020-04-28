package com.ulan.app.munduz.dagger.modules.activitymodule

import android.content.Context
import com.ulan.app.munduz.dagger.scopes.DetailsScope
import com.ulan.app.munduz.dagger.scopes.OrdersScope
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.orders.OrdersActivity
import com.ulan.app.munduz.ui.orders.OrdersPresenter
import com.ulan.app.munduz.ui.orders.OrdersPresenterImpl
import com.ulan.app.munduz.ui.orders.OrdersView
import dagger.Module
import dagger.Provides

@Module
class OrdersModule {

    @OrdersScope
    @Provides
    fun ordersActivity(activity: OrdersActivity) : OrdersView{
        return activity
    }

    @OrdersScope
    @Provides
    fun ordersPresenter(view: OrdersView): OrdersPresenter{
        return OrdersPresenterImpl(view)
    }

}