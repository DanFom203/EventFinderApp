package com.itis.eventfinderapp.di.app

import android.content.Context
import com.itis.common.di.scope.ApplicationScope
import com.itis.eventfinderapp.App
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @ApplicationScope
    @Provides
    fun provideContext(application: App): Context {

        return application
    }
}