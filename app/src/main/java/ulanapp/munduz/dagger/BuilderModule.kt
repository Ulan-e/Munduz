package ulanapp.munduz.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ulanapp.munduz.ui.activities.details.DetailsActivity
import ulanapp.munduz.ui.activities.details.DetailsModule
import ulanapp.munduz.ui.activities.details.DetailsScope
import ulanapp.munduz.ui.activities.main.MainActivity
import ulanapp.munduz.ui.activities.main.MainModule
import ulanapp.munduz.ui.activities.main.MainScope
import ulanapp.munduz.ui.activities.orders.OrdersActivity
import ulanapp.munduz.ui.activities.orders.OrdersModule
import ulanapp.munduz.ui.activities.orders.OrdersScope
import ulanapp.munduz.ui.activities.search.SearchActivity
import ulanapp.munduz.ui.activities.search.SearchModule
import ulanapp.munduz.ui.activities.search.SearchScope
import ulanapp.munduz.ui.fragments.basket.BasketFragment
import ulanapp.munduz.ui.fragments.basket.BasketModule
import ulanapp.munduz.ui.fragments.catalog.CatalogFragment
import ulanapp.munduz.ui.fragments.catalog.CatalogModule
import ulanapp.munduz.ui.fragments.favorite.FavoriteFragment
import ulanapp.munduz.ui.fragments.favorite.FavoriteModule
import ulanapp.munduz.ui.fragments.filtered.FilteredFragment
import ulanapp.munduz.ui.fragments.filtered.FilteredModule
import ulanapp.munduz.ui.fragments.filtered.FilteredScope
import ulanapp.munduz.ui.fragments.home.HomeFragment
import ulanapp.munduz.ui.fragments.home.HomeModule
import ulanapp.munduz.ui.fragments.more.MoreFragment
import ulanapp.munduz.ui.fragments.more.MoreModule
import ulanapp.munduz.ui.fragments.more.sections.AboutAppFragment
import ulanapp.munduz.ui.fragments.more.sections.ContactUsFragment
import ulanapp.munduz.ui.fragments.more.sections.WriteToUsFragment
import ulanapp.munduz.ui.fragments.purchase.PurchaseFragment
import ulanapp.munduz.ui.fragments.purchase.PurchaseModule

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