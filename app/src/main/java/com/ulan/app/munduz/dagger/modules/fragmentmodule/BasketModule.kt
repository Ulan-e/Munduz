package com.ulan.app.munduz.dagger.modules.fragmentmodule

import android.content.Context
import com.ulan.app.munduz.adapter.BasketAdapter
import com.ulan.app.munduz.dagger.modules.RoomModule
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepositoryImpl
import com.ulan.app.munduz.listeners.OnItemBasketClickListener
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.basket.BasketFragment
import com.ulan.app.munduz.ui.basket.BasketPresenter
import com.ulan.app.munduz.ui.basket.BasketPresenterImpl
import com.ulan.app.munduz.ui.basket.BasketView
import dagger.Module
import dagger.Provides


@Module(includes = [RoomModule::class])
class BasketModule {

    @MainScope
    @Provides
    fun provideView(fragment: BasketFragment): BasketView {
        return fragment
    }

    @MainScope
    @Provides
    fun providePresenter(
        view: BasketView,
        firebaseRepository: FirebaseRepository,
        purchasesRepository: PurchasesRepository
    ): BasketPresenter {
        return BasketPresenterImpl(view, firebaseRepository, purchasesRepository)
    }

    @MainScope
    @Provides
    fun provideListener(fragment: BasketFragment): OnItemBasketClickListener {
        return fragment
    }

    @MainScope
    @Provides
    fun provideAdapter(context: Context, listener: OnItemBasketClickListener): BasketAdapter {
        return BasketAdapter(context, listener)
    }
}