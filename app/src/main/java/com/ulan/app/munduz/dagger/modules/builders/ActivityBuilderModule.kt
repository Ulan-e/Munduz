package com.ulan.app.munduz.dagger.modules.builders

import com.ulan.app.munduz.dagger.modules.RepositoryModule
import com.ulan.app.munduz.dagger.modules.activitymodule.DetailsModule
import com.ulan.app.munduz.dagger.modules.fragmentmodule.FilteredModule
import com.ulan.app.munduz.dagger.modules.activitymodule.MainModule
import com.ulan.app.munduz.dagger.modules.activitymodule.SearchModule
import com.ulan.app.munduz.dagger.scopes.DetailsScope
import com.ulan.app.munduz.dagger.scopes.FilteredScope
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.dagger.scopes.SearchScope
import com.ulan.app.munduz.ui.details.DetailsActivity
import com.ulan.app.munduz.ui.filtered.FilteredFragment
import com.ulan.app.munduz.ui.main.MainActivity
import com.ulan.app.munduz.ui.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module(includes = [RepositoryModule::class])
abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

    @DetailsScope
    @ContributesAndroidInjector(modules = [DetailsModule::class])
    abstract fun detailsActivity(): DetailsActivity

    @FilteredScope
    @ContributesAndroidInjector(modules = [FilteredModule::class])
    abstract fun filteredActivity(): FilteredFragment

    @SearchScope
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun searchActivity(): SearchActivity
}