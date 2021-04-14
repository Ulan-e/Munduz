package ulanapp.munduz.ui.activities.details

import dagger.Binds
import dagger.Module
import ulanapp.munduz.dagger.RoomModule

@Module(includes = [RoomModule::class])
abstract class DetailsModule {

    @DetailsScope
    @Binds
    abstract fun detailsActivity(detailsActivity: DetailsActivity): DetailsView
}