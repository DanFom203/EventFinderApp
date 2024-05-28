package com.itis.feature.notes.impl.di

import com.itis.common.core.resources.ResourceManager
import com.itis.common.utils.DateFormatter
import kotlinx.coroutines.CoroutineDispatcher

interface NotesFeatureDependencies {

    fun resourceManager(): ResourceManager

    fun dateFormatter(): DateFormatter

    fun coroutineDispatcher(): CoroutineDispatcher
}