package com.ulan.app.munduz.ui.fragments.catalog

import android.content.Context
import com.ulan.app.munduz.interfaces.OnCategoryClickListener
import com.ulan.app.munduz.ui.activities.main.MainScope
import com.ulan.app.munduz.ui.adapter.CatalogAdapter
import dagger.Module
import dagger.Provides

@Module
class CatalogModule {

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