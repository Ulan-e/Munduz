package com.ulan.app.munduz.dagger.modules.builders

import com.ulan.app.munduz.dagger.modules.RepositoryModule
import com.ulan.app.munduz.dagger.modules.fragmentmodule.*
import com.ulan.app.munduz.dagger.scopes.DetailsScope
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.ui.buy.BuyFragment
import com.ulan.app.munduz.ui.catalog.CatalogFragment
import com.ulan.app.munduz.ui.home.HomeFragment
import com.ulan.app.munduz.ui.liked.LikedFragment
import com.ulan.app.munduz.ui.more.MoreFragment
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
    @ContributesAndroidInjector(modules = [LikedModule::class])
    abstract fun likedFragment(): LikedFragment

    @MainScope
    @ContributesAndroidInjector(modules = [MoreModule::class])
    abstract fun moreFragment(): MoreFragment

    @DetailsScope
    @ContributesAndroidInjector(modules = [BuyModule::class])
    abstract fun buyFragment(): BuyFragment


    @MainScope
    @ContributesAndroidInjector
    abstract fun contactUsFragmet(): ContactUsFragment

    @MainScope
    @ContributesAndroidInjector
    abstract fun writeUsFragment(): WriteToUsFragment



}