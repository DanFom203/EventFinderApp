package com.itis.common.di

import android.content.Context
import com.itis.common.core.config.AppProperties
import com.itis.common.core.preferences.Preferences
import com.itis.common.core.resources.ResourceManager
import kotlinx.coroutines.CoroutineDispatcher

interface CommonApi {

    fun applicationContext(): Context

    fun provideResourceManager(): ResourceManager

    fun provideAppProperties(): AppProperties
    fun provideCoroutineDispatcher(): CoroutineDispatcher
    fun providePreferences(): Preferences
}