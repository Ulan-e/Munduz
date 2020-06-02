package ulanapp.munduz.ui.fragments.home

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ulanapp.munduz.dagger.RoomModule
import ulanapp.munduz.interfaces.OnItemClickListener
import ulanapp.munduz.ui.activities.main.MainScope
import ulanapp.munduz.ui.adapter.ProductsAdapter

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