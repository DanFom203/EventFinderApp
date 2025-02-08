package com.itis.feature.events.impl.domain.usecase

import com.itis.feature.events.api.domain.repository.EventsRepository
import com.itis.feature.events.api.domain.response.EventInfoResponse
import com.itis.feature.events.api.presentation.model.EventInfoUiModel
import com.itis.feature.events.impl.data.mapper.EventInfoDomainModelMapper
import com.itis.feature.events.impl.domain.mapper.EventInfoUiModelMapper
import com.itis.feature.events.impl.domain.model.EventInfoDomainModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetEventInfoUseCaseTest {

    private lateinit var useCase: GetEventInfoUseCase
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var repository: EventsRepository

    @MockK
    lateinit var dataMapper: EventInfoDomainModelMapper

    @MockK
    lateinit var domainMapper: EventInfoUiModelMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = GetEventInfoUseCase(dataMapper, domainMapper, repository, testDispatcher)
    }

    @Test
    fun `invoke должен вернуть UI модель детальной информации о мероприятии`() = runTest(testDispatcher) {
        val eventId = 123
        val response = mockk<EventInfoResponse>()
        val domainModel = mockk<EventInfoDomainModel>()
        val uiModel = mockk<EventInfoUiModel>()

        coEvery { repository.getEventInfo(eventId) } returns response
        coEvery { dataMapper.mapResponseToDomainModel(response) } returns domainModel
        coEvery { domainMapper.mapDomainToUiModel(domainModel) } returns uiModel

        val result = useCase(eventId)

        assertEquals(uiModel, result)
    }
}