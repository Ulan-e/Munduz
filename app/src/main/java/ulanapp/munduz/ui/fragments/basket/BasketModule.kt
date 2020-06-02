package ulanapp.munduz.ui.fragments.basket

import android.content.Context
import dagger.Module
import dagger.Provides
import ulanapp.munduz.dagger.RoomModule
import ulanapp.munduz.interfaces.OnItemBasketClickListener
import ulanapp.munduz.ui.activities.main.MainScope
import ulanapp.munduz.ui.adapter.BasketAdapter


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