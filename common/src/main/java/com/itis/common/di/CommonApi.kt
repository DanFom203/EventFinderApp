package com.itis.common.di

import android.content.Context
import com.itis.common.core.config.AppProperties
import com.itis.common.core.preferences.Preferences
import com.itis.common.core.resources.ResourceManager
import com.itis.common.utils.DateFormatter
import kotlinx.coroutines.CoroutineDispatcher

interface CommonApi {

    fun applicationContext(): Context

    fun provideResourceManager(): ResourceManager

    fun provideDateFormatter(): DateFormatter

    fun provideAppProperties(): AppProperties

    fun provideCoroutineDispatcher(): CoroutineDispatcher

    fun providePreferences(): Preferences
}