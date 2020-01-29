package com.ulan.app.munduz.dagger.modules.activitymodule

import android.content.Context
import android.widget.AdapterView
import com.ulan.app.munduz.adapter.ProductAdapter
import com.ulan.app.munduz.adapter.SearchResultsAdapter
import com.ulan.app.munduz.dagger.scopes.SearchScope
import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.search.SearchActivity
import com.ulan.app.munduz.ui.search.SearchPresenter
import com.ulan.app.munduz.ui.search.SearchPresenterImpl
import com.ulan.app.munduz.ui.search.SearchView
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @SearchScope
    @Provides
    fun searchActivity(searchActivity: SearchActivity): SearchView{
        return searchActivity
    }

    @SearchScope
    @Provides
    fun searchPresenter(searchView: SearchView, repository: Repository): SearchPresenter{
        return SearchPresenterImpl(searchView, repository)
    }

    @SearchScope
    @Provides
    fun clickListener(searchActivity: SearchActivity): OnItemClickListener {
        return searchActivity
    }

    @SearchScope
    @Provides
    fun searchAdapter(context: Context, clickListener: OnItemClickListener): SearchResultsAdapter{
        return SearchResultsAdapter(context, clickListener)
    }

}