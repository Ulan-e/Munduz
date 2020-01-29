package com.ulan.app.munduz.dagger.modules.fragmentmodule

import android.content.Context
import com.ulan.app.munduz.adapter.ProductAdapter
import com.ulan.app.munduz.dagger.modules.RoomModule
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.data.roomdatabase.LikedDatabase
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.liked.LikedFragment
import com.ulan.app.munduz.ui.liked.LikedPresenter
import com.ulan.app.munduz.ui.liked.LikedPresenterImpl
import com.ulan.app.munduz.ui.liked.LikedView
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [RoomModule::class])
class LikedModule{


    @MainScope
    @Provides
    fun likedView(likedFragment: LikedFragment): LikedView{
        return likedFragment
    }

    @MainScope
    @Provides
    fun likedPresenter(view: LikedView, database: LikedDatabase, repository: Repository): LikedPresenter{
        return LikedPresenterImpl(view, database, repository)
    }

    @MainScope
    @Provides
    fun provideListener(likedFragment: LikedFragment): OnItemClickListener{
        return likedFragment
    }

    @MainScope
    @Provides
    fun provideAdapter(context: Context, listener: OnItemClickListener): ProductAdapter {
        return ProductAdapter(context, listener)
    }
}