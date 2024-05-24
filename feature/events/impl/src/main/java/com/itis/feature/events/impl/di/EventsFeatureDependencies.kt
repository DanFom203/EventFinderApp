package com.itis.feature.events.impl.di

import com.itis.common.core.resources.ResourceManager
import com.itis.common.utils.CityFormatter
import com.itis.common.utils.DateFormatter
import kotlinx.coroutines.CoroutineDispatcher

interface EventsFeatureDependencies {

    fun resourceManager(): ResourceManager

    fun dateFormatter(): DateFormatter

    fun cityFormatter(): CityFormatter

    fun coroutineDispatcher(): CoroutineDispatcher
}