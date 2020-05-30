package com.ulan.app.munduz.ui.filtered

import android.content.Context
import com.ulan.app.munduz.adapter.ProductsAdapter
import com.ulan.app.munduz.dagger.modules.RoomModule
import com.ulan.app.munduz.listeners.OnItemClickListener
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [RoomModule::class])
abstract class FilteredModule {

    @FilteredScope
    @Binds
    abstract fun filteredView(filteredFragment: FilteredFragment): FilteredView

    @FilteredScope
    @Binds
    abstract fun filteredPresenter(presenter: FilteredPresenterImpl): FilteredPresenter

    @Module
    companion object {

        @JvmStatic
        @FilteredScope
        @Provides
        fun productsAdapter(context: Context, clickListener: OnItemClickListener): ProductsAdapter {
            return ProductsAdapter(context, clickListener)
        }

        @JvmStatic
        @FilteredScope
        @Provides
        fun clickListener(filteredFragment: FilteredFragment): OnItemClickListener {
            return filteredFragment
        }
    }

}