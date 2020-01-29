package com.ulan.app.munduz.dagger.modules

import android.content.Context
import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
class RepositoryModule{


    @Provides
    fun repository(context: Context): Repository{
        return RepositoryImpl(context)
    }
}

