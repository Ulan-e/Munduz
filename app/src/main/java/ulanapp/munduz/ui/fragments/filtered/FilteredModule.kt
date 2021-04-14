package ulanapp.munduz.ui.fragments.filtered

import android.content.Context
import dagger.Module
import dagger.Provides
import ulanapp.munduz.dagger.RoomModule
import ulanapp.munduz.interfaces.OnItemClickListener
import ulanapp.munduz.ui.adapter.ProductsAdapter

@Module(includes = [RoomModule::class])
abstract class FilteredModule {

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