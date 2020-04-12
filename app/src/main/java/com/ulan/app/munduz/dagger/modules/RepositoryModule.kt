package com.ulan.app.munduz.dagger.modules

import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.firebase.FirebaseRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun repository(): FirebaseRepository {
        return FirebaseRepositoryImpl()
    }
}

