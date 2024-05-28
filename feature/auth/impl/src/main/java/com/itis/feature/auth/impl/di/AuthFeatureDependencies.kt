package com.itis.feature.auth.impl.di

import com.itis.common.core.preferences.Preferences
import com.itis.common.core.resources.ResourceManager
import com.itis.common.utils.CityFormatter
import kotlinx.coroutines.CoroutineDispatcher

interface AuthFeatureDependencies {
    fun resourceManager(): ResourceManager

    fun coroutineDispatcher(): CoroutineDispatcher

    fun preferences(): Preferences

    fun cityFormatter(): CityFormatter
}