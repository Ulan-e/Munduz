package com.ulan.app.munduz.dagger.modules.fragmentmodule

import android.content.Context
import com.ulan.app.munduz.adapter.FavoriteProductAdapter
import com.ulan.app.munduz.dagger.modules.RoomModule
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.data.roomdatabase.LikedDatabase
import com.ulan.app.munduz.listeners.OnFavoriteItemClickListener
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.favorite.FavoriteFragment
import com.ulan.app.munduz.ui.favorite.FavoritePresenter
import com.ulan.app.munduz.ui.favorite.FavoritePresenterImpl
import com.ulan.app.munduz.ui.favorite.FavoriteView
import dagger.Module
import dagger.Provides

@Module(includes = [RoomModule::class])
class FavoriteModule{


    @MainScope
    @Provides
    fun likedView(likedFragment: FavoriteFragment): FavoriteView{
        return likedFragment
    }

    @MainScope
    @Provides
    fun likedPresenter(view: FavoriteView, database: LikedDatabase, repository: Repository): FavoritePresenter{
        return FavoritePresenterImpl(view, database, repository)
    }

    @MainScope
    @Provides
    fun provideListener(likedFragment: FavoriteFragment): OnFavoriteItemClickListener{
        return likedFragment
    }

    @MainScope
    @Provides
    fun provideAdapter(context: Context, listener: OnFavoriteItemClickListener): FavoriteProductAdapter {
        return FavoriteProductAdapter(context, listener)
    }
}