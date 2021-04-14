package ulanapp.munduz.ui.fragments.more

import dagger.Binds
import dagger.Module
import ulanapp.munduz.ui.activities.main.MainScope

@Module
abstract class MoreModule {

    @MainScope
    @Binds
    abstract fun moreView(moreFragment: MoreFragment): MoreView
}