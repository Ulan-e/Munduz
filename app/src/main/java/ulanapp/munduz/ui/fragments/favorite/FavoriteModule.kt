package ulanapp.munduz.ui.fragments.favorite

import android.content.Context
import dagger.Module
import dagger.Provides
import ulanapp.munduz.dagger.RoomModule
import ulanapp.munduz.interfaces.OnItemClickListener
import ulanapp.munduz.ui.activities.main.MainScope
import ulanapp.munduz.ui.adapter.FavoritesAdapter

@Module(includes = [RoomModule::class])
class FavoriteModule {

    @MainScope
    @Provides
    fun clickListener(fragment: FavoriteFragment): OnItemClickListener {
        return fragment
    }

    @MainScope
    @Provides
    fun favoriteAdapter(context: Context, listener: OnItemClickListener): FavoritesAdapter {
        return FavoritesAdapter(context, listener)
    }
}