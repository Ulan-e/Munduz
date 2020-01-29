package com.ulan.app.munduz.dagger.modules.fragmentmodule

import android.content.Context
import com.ulan.app.munduz.adapter.CatalogAdapter
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.listeners.OnCategoryClickListener
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.catalog.CatalogFragment
import com.ulan.app.munduz.ui.catalog.CatalogPresenter
import com.ulan.app.munduz.ui.catalog.CatalogPresenterImpl
import com.ulan.app.munduz.ui.catalog.CatalogView
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class CatalogModule {

    @MainScope
    @Provides
    fun catalogView(catalogFragment: CatalogFragment): CatalogView {
        return catalogFragment
    }

    @MainScope
    @Provides
    fun catalogPresenter(catalogView: CatalogView, repository: Repository): CatalogPresenter {
        return CatalogPresenterImpl(catalogView, repository)
    }

    @MainScope
    @Provides
    fun clickListener(catalogFragment: CatalogFragment): OnCategoryClickListener {
        return catalogFragment
    }

    @MainScope
    @Provides
    fun catalogAdapter(context: Context, clickListener: OnCategoryClickListener): CatalogAdapter {
        return CatalogAdapter(context, clickListener)
    }

}