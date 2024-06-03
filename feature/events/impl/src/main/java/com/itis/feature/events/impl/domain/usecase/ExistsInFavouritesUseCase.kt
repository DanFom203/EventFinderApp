package com.itis.feature.events.impl.domain.usecase

import com.itis.feature.events.api.domain.repository.EventsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExistsInFavouritesUseCase @Inject constructor(
    private val repository: EventsRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(eventId: Int): Boolean {
        return withContext(coroutineDispatcher) {
            repository.existsInFirestoreDb(eventId = eventId)
        }
    }
}