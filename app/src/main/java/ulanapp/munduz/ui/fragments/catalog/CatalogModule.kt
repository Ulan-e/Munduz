package ulanapp.munduz.ui.fragments.catalog

import android.content.Context
import dagger.Module
import dagger.Provides
import ulanapp.munduz.interfaces.OnCategoryClickListener
import ulanapp.munduz.ui.activities.main.MainScope
import ulanapp.munduz.ui.adapter.CatalogAdapter

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