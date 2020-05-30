package com.ulan.app.munduz.ui.home

import android.content.Context
import com.ulan.app.munduz.adapter.ProductsAdapter
import com.ulan.app.munduz.dagger.modules.RoomModule
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.main.MainScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [RoomModule::class])
abstract class HomeModule {

    @MainScope
    @Binds
    abstract fun homeView(homeFragment: HomeFragment): HomeView

    @MainScope
    @Binds
    abstract fun homePresenter(homePresenter: HomePresenterImpl): HomePresenter

    @Module
    companion object {

        @JvmStatic
        @MainScope
        @Provides
        fun productsAdapter(context: Context, listener: OnItemClickListener): ProductsAdapter {
            return ProductsAdapter(context, listener)
        }

        @JvmStatic
        @MainScope
        @Provides
        fun clickListener(homeFragment: HomeFragment): OnItemClickListener {
            return homeFragment
        }
    }
}