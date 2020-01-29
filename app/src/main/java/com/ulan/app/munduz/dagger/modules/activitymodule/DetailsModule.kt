package com.ulan.app.munduz.dagger.modules.activitymodule

import com.ulan.app.munduz.dagger.modules.RoomModule
import com.ulan.app.munduz.dagger.scopes.DetailsScope
import com.ulan.app.munduz.ui.details.DetailsActivity
import com.ulan.app.munduz.ui.details.DetailsPresenter
import com.ulan.app.munduz.ui.details.DetailsPresenterImpl
import com.ulan.app.munduz.ui.details.DetailsView
import dagger.Binds
import dagger.Module

@Module(includes = [RoomModule::class])
abstract class DetailsModule {


    @DetailsScope
    @Binds
    abstract fun detailsActivity(detailsActivity: DetailsActivity): DetailsView

    @DetailsScope
    @Binds
    abstract fun detailsPresenter(detailsPresenter: DetailsPresenterImpl): DetailsPresenter

}