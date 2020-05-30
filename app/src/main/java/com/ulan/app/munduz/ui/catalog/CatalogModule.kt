package com.ulan.app.munduz.ui.catalog

import android.content.Context
import com.ulan.app.munduz.adapter.CatalogAdapter
import com.ulan.app.munduz.listeners.OnCategoryClickListener
import com.ulan.app.munduz.ui.main.MainScope
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
    fun catalogPresenter(catalogView: CatalogView): CatalogPresenter {
        return CatalogPresenterImpl(catalogView)
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