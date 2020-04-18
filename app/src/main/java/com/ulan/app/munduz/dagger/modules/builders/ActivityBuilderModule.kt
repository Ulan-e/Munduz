package com.ulan.app.munduz.dagger.modules.builders

import com.ulan.app.munduz.dagger.modules.RepositoryModule
import com.ulan.app.munduz.dagger.modules.activitymodule.BuyModule
import com.ulan.app.munduz.dagger.modules.activitymodule.DetailsModule
import com.ulan.app.munduz.dagger.modules.activitymodule.MainModule
import com.ulan.app.munduz.dagger.modules.activitymodule.SearchModule
import com.ulan.app.munduz.dagger.scopes.DetailsScope
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.dagger.scopes.SearchScope
import com.ulan.app.munduz.ui.buy.BuyActivity
import com.ulan.app.munduz.ui.details.DetailsActivity
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

    @SearchScope
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun searchActivity(): SearchActivity

    @DetailsScope
    @ContributesAndroidInjector(modules = [BuyModule::class])
    abstract fun buyActivity(): BuyActivity
}