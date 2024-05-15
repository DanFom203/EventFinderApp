package com.itis.common.di.modules

import android.content.Context
import com.itis.common.core.preferences.Preferences
import com.itis.common.storage.PreferencesImpl
import com.itis.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class PreferencesModule {
    @Provides
    @ApplicationScope
    fun providePreferences(context: Context): Preferences {
        return PreferencesImpl(context)
    }
}