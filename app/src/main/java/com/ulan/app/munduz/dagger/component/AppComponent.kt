package com.ulan.app.munduz.dagger.component

import com.ulan.app.munduz.BaseApplication
import com.ulan.app.munduz.dagger.modules.builders.ActivityBuilderModule
import com.ulan.app.munduz.dagger.modules.AppModule
import com.ulan.app.munduz.dagger.modules.builders.FragmentBuilderModule
import com.ulan.app.munduz.dagger.scopes.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilderModule::class, FragmentBuilderModule::class])
interface AppComponent : AndroidInjector<BaseApplication>{
    
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: BaseApplication): AppComponent.Builder
        fun build(): AppComponent
    }

}