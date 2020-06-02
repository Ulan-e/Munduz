package ulanapp.munduz.ui.activities.search

import android.content.Context
import dagger.Module
import dagger.Provides
import ulanapp.munduz.interfaces.OnItemClickListener
import ulanapp.munduz.ui.adapter.SearchAdapter

@Module
class SearchModule {

    @SearchScope
    @Provides
    fun searchActivity(searchActivity: SearchActivity): SearchView {
        return searchActivity
    }

    @SearchScope
    @Provides
    fun clickListener(searchActivity: SearchActivity): OnItemClickListener {
        return searchActivity
    }

    @SearchScope
    @Provides
    fun searchAdapter(context: Context, clickListener: OnItemClickListener): SearchAdapter {
        return SearchAdapter(context, clickListener)
    }

}