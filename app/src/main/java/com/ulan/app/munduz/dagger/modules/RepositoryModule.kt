package com.ulan.app.munduz.dagger.modules

import android.content.Context
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.firebase.FirebaseRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
class RepositoryModule {

    @Provides
    fun repository(context: Context): FirebaseRepository {
        return FirebaseRepositoryImpl(context)
    }
}

