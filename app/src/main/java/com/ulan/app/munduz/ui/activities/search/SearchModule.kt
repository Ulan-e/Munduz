package com.ulan.app.munduz.ui.activities.search

import android.content.Context
import com.ulan.app.munduz.ui.adapter.SearchAdapter
import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.interfaces.OnItemClickListener
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @SearchScope
    @Provides
    fun searchActivity(searchActivity: SearchActivity): SearchView {
        return searchActivity
    }

    @SearchScope
    @Provides
    fun searchPresenter(searchView: SearchView, repository: FirebaseRepository): SearchPresenter {
        return SearchPresenterImpl(searchView, repository)
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