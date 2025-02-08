package com.itis.feature.events.impl.domain.usecase

import com.itis.feature.events.api.domain.repository.EventsRepository
import com.itis.feature.events.api.domain.response.EventInfoResponse
import com.itis.feature.events.api.presentation.model.EventInfoUiModel
import com.itis.feature.events.impl.data.mapper.EventInfoDomainModelMapper
import com.itis.feature.events.impl.domain.mapper.EventInfoUiModelMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetEventInfoUseCase @Inject constructor(
    private val dataMapper: EventInfoDomainModelMapper,
    private val domainMapper: EventInfoUiModelMapper,
    private val repository: EventsRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(eventId: Int): EventInfoUiModel {
        val eventInfoResponse: EventInfoResponse = repository.getEventInfo(eventId)
        return withContext(coroutineDispatcher) {
            dataMapper.mapResponseToDomainModel(eventInfoResponse)
                ?.let { domainMapper.mapDomainToUiModel(it) }!!
        }
    }
}