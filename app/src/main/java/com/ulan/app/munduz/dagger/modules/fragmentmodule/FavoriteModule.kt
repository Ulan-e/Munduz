package com.ulan.app.munduz.dagger.modules.fragmentmodule

import android.content.Context
import com.ulan.app.munduz.adapter.FavoriteProductAdapter
import com.ulan.app.munduz.dagger.modules.RoomModule
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.room.repository.KeysRepositoryImpl
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
    fun provideView(likedFragment: FavoriteFragment): FavoriteView {
        return likedFragment
    }

    @MainScope
    @Provides
    fun providePresenter(
        view: FavoriteView,
        firebaseRepository: FirebaseRepository,
        keysRepository: KeysRepositoryImpl
    ): FavoritePresenter {
        return FavoritePresenterImpl(view, firebaseRepository, keysRepository)
    }

    @MainScope
    @Provides
    fun provideListener(fragment: FavoriteFragment): OnItemClickListener {
        return fragment
    }

    @MainScope
    @Provides
    fun provideAdapter(context: Context, listener: OnItemClickListener): FavoriteProductAdapter {
        return FavoriteProductAdapter(context, listener)
    }
}