package com.ulan.app.munduz.dagger.modules

import com.ulan.app.munduz.ui.fragments.basket.BasketFragment
import com.ulan.app.munduz.ui.fragments.basket.BasketModule
import com.ulan.app.munduz.ui.fragments.catalog.CatalogFragment
import com.ulan.app.munduz.ui.fragments.catalog.CatalogModule
import com.ulan.app.munduz.ui.activities.details.DetailsActivity
import com.ulan.app.munduz.ui.activities.details.DetailsModule
import com.ulan.app.munduz.ui.activities.details.DetailsScope
import com.ulan.app.munduz.ui.fragments.favorite.FavoriteFragment
import com.ulan.app.munduz.ui.fragments.favorite.FavoriteModule
import com.ulan.app.munduz.ui.fragments.filtered.FilteredFragment
import com.ulan.app.munduz.ui.fragments.filtered.FilteredModule
import com.ulan.app.munduz.ui.fragments.filtered.FilteredScope
import com.ulan.app.munduz.ui.fragments.home.HomeFragment
import com.ulan.app.munduz.ui.fragments.home.HomeModule
import com.ulan.app.munduz.ui.activities.main.MainActivity
import com.ulan.app.munduz.ui.activities.main.MainModule
import com.ulan.app.munduz.ui.activities.main.MainScope
import com.ulan.app.munduz.ui.fragments.more.MoreFragment
import com.ulan.app.munduz.ui.fragments.more.MoreModule
import com.ulan.app.munduz.ui.fragments.more.sections.AboutAppFragment
import com.ulan.app.munduz.ui.fragments.more.sections.ContactUsFragment
import com.ulan.app.munduz.ui.fragments.more.sections.WriteToUsFragment
import com.ulan.app.munduz.ui.activities.orders.OrdersActivity
import com.ulan.app.munduz.ui.activities.orders.OrdersModule
import com.ulan.app.munduz.ui.activities.orders.OrdersScope
import com.ulan.app.munduz.ui.fragments.purchase.PurchaseFragment
import com.ulan.app.munduz.ui.fragments.purchase.PurchaseModule
import com.ulan.app.munduz.ui.activities.search.SearchActivity
import com.ulan.app.munduz.ui.activities.search.SearchModule
import com.ulan.app.munduz.ui.activities.search.SearchScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [FirebaseModule::class])
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

    @OrdersScope
    @ContributesAndroidInjector(modules = [OrdersModule::class])
    abstract fun ordersActivity(): OrdersActivity
}

@Module(includes = [FirebaseModule::class])
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

    @OrdersScope
    @ContributesAndroidInjector(modules = [PurchaseModule::class])
    abstract fun purchaseFragment(): PurchaseFragment

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