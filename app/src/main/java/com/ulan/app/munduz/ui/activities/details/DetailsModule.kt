package com.ulan.app.munduz.ui.activities.details

import com.ulan.app.munduz.dagger.modules.RoomModule
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