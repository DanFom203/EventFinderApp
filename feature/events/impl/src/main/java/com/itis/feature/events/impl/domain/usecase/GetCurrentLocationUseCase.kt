package com.itis.feature.events.impl.domain.usecase

import com.itis.feature.events.api.domain.repository.EventsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val repository: EventsRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): String {

        return withContext(coroutineDispatcher) {
            repository.getUserLocation()
        }
    }
}