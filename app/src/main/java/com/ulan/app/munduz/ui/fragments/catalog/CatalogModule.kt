package com.ulan.app.munduz.ui.fragments.catalog

import android.content.Context
import com.ulan.app.munduz.ui.adapter.CatalogAdapter
import com.ulan.app.munduz.interfaces.OnCategoryClickListener
import com.ulan.app.munduz.ui.activities.main.MainScope
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