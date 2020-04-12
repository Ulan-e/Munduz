package com.ulan.app.munduz.dagger.modules.fragmentmodule

import android.content.Context
import com.ulan.app.munduz.adapter.FavoritesAdapter
import com.ulan.app.munduz.dagger.modules.RoomModule
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.favorite.FavoriteFragment
import com.ulan.app.munduz.ui.favorite.FavoritePresenter
import com.ulan.app.munduz.ui.favorite.FavoritePresenterImpl
import com.ulan.app.munduz.ui.favorite.FavoriteView
import dagger.Module
import dagger.Provides

@Module(includes = [RoomModule::class])
class FavoriteModule {

    @MainScope
    @Provides
    fun favoriteView(fragment: FavoriteFragment): FavoriteView {
        return fragment
    }

    @MainScope
    @Provides
    fun favoritePresenter(
        view: FavoriteView,
        firebaseRepository: FirebaseRepository,
        favoriteRepository: FavoritesRepository
    ): FavoritePresenter {
        return FavoritePresenterImpl(view, firebaseRepository, favoriteRepository)
    }

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