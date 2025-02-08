package com.itis.feature.events.impl.domain.usecase

import com.itis.feature.events.api.domain.repository.EventsRepository
import com.itis.feature.events.api.domain.response.EventsListResponse
import com.itis.feature.events.impl.data.mapper.EventsListDomainModelMapper
import com.itis.feature.events.impl.domain.mapper.EventsListUiModelMapper
import com.itis.feature.events.api.presentation.model.EventsListUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentEventsUseCase @Inject constructor(
    private val dataMapper: EventsListDomainModelMapper,
    private val domainMapper:EventsListUiModelMapper,
    private val repository: EventsRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(location: String): EventsListUiModel {
        val eventsResponse: EventsListResponse = repository.getEvents(location = location)
        return withContext(coroutineDispatcher) {
            dataMapper.mapResponseToDomainModel(eventsResponse)
                ?.let { domainMapper.mapDomainToUiModel(it) }!!
        }
    }
}