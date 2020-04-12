package com.ulan.app.munduz.dagger.modules.activitymodule

import android.content.Context
import com.ulan.app.munduz.adapter.SearchAdapter
import com.ulan.app.munduz.dagger.scopes.SearchScope
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.search.SearchActivity
import com.ulan.app.munduz.ui.search.SearchPresenter
import com.ulan.app.munduz.ui.search.SearchPresenterImpl
import com.ulan.app.munduz.ui.search.SearchView
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
    fun searchPresenter(searchView: SearchView, repository: FirebaseRepository): SearchPresenter{
        return SearchPresenterImpl(searchView, repository)
    }

    @SearchScope
    @Provides
    fun clickListener(searchActivity: SearchActivity): OnItemClickListener {
        return searchActivity
    }

    @SearchScope
    @Provides
    fun searchAdapter(context: Context, clickListener: OnItemClickListener): SearchAdapter{
        return SearchAdapter(context, clickListener)
    }

}