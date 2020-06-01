package com.ulan.app.munduz.ui.fragments.home

import android.content.Context
import com.ulan.app.munduz.ui.adapter.ProductsAdapter
import com.ulan.app.munduz.dagger.modules.RoomModule
import com.ulan.app.munduz.interfaces.OnItemClickListener
import com.ulan.app.munduz.ui.activities.main.MainScope
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