package com.itis.feature.events.impl.di

import com.itis.common.core.resources.ResourceManager
import kotlinx.coroutines.CoroutineDispatcher

interface EventsFeatureDependencies {

    fun resourceManager(): ResourceManager

    fun coroutineDispatcher(): CoroutineDispatcher
}