package com.ulan.app.munduz.ui.fragments.basket

import android.content.Context
import com.ulan.app.munduz.ui.adapter.BasketAdapter
import com.ulan.app.munduz.dagger.modules.RoomModule
import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.interfaces.OnItemBasketClickListener
import com.ulan.app.munduz.ui.activities.main.MainScope
import dagger.Module
import dagger.Provides


@Module(includes = [RoomModule::class])
class BasketModule {

    @MainScope
    @Provides
    fun basketListener(fragment: BasketFragment): OnItemBasketClickListener {
        return fragment
    }

    @MainScope
    @Provides
    fun basketAdapter(context: Context, listener: OnItemBasketClickListener): BasketAdapter {
        return BasketAdapter(context, listener)
    }
}