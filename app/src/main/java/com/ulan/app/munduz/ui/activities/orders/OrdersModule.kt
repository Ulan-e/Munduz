package com.ulan.app.munduz.ui.activities.orders

import dagger.Module
import dagger.Provides

@Module
class OrdersModule {

    @OrdersScope
    @Provides
    fun ordersActivity(activity: OrdersActivity): OrdersView {
        return activity
    }

}