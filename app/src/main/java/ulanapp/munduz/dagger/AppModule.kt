package ulanapp.munduz.dagger

import android.content.Context
import dagger.Binds
import dagger.Module
import ulanapp.munduz.BaseApplication
import javax.inject.Scope

@Module
abstract class AppModule {

    @Binds
    abstract fun context(application: BaseApplication): Context

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope