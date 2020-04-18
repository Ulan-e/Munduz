package com.ulan.app.munduz.dagger.modules.builders

import com.ulan.app.munduz.dagger.modules.RepositoryModule
import com.ulan.app.munduz.dagger.modules.fragmentmodule.*
import com.ulan.app.munduz.dagger.scopes.FilteredScope
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.ui.basket.BasketFragment
import com.ulan.app.munduz.ui.catalog.CatalogFragment
import com.ulan.app.munduz.ui.home.HomeFragment
import com.ulan.app.munduz.ui.favorite.FavoriteFragment
import com.ulan.app.munduz.ui.filtered.FilteredFragment
import com.ulan.app.munduz.ui.more.MoreFragment
import com.ulan.app.munduz.ui.more.sections.AboutAppFragment
import com.ulan.app.munduz.ui.more.sections.ContactUsFragment
import com.ulan.app.munduz.ui.more.sections.WriteToUsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [RepositoryModule::class])
abstract class FragmentBuilderModule {

    @MainScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun homeFragment(): HomeFragment

    @MainScope
    @ContributesAndroidInjector(modules = [CatalogModule::class])
    abstract fun catalogFragment(): CatalogFragment

    @MainScope
    @ContributesAndroidInjector(modules = [FavoriteModule::class])
    abstract fun favoriteFragment(): FavoriteFragment

    @MainScope
    @ContributesAndroidInjector(modules = [BasketModule::class])
    abstract fun basketFragment(): BasketFragment

    @MainScope
    @ContributesAndroidInjector(modules = [MoreModule::class])
    abstract fun moreFragment(): MoreFragment

    @FilteredScope
    @ContributesAndroidInjector(modules = [FilteredModule::class])
    abstract fun filteredFragment(): FilteredFragment

    @MainScope
    @ContributesAndroidInjector
    abstract fun contactUsFragment(): ContactUsFragment

    @MainScope
    @ContributesAndroidInjector
    abstract fun writeUsFragment(): WriteToUsFragment

    @MainScope
    @ContributesAndroidInjector
    abstract fun aboutAppFragment(): AboutAppFragment

}