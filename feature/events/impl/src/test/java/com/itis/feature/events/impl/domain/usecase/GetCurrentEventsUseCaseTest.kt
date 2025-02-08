package com.itis.feature.events.impl.domain.usecase

import com.itis.feature.events.api.domain.repository.EventsRepository
import com.itis.feature.events.api.domain.response.EventsListResponse
import com.itis.feature.events.api.presentation.model.EventsListUiModel
import com.itis.feature.events.impl.data.mapper.EventsListDomainModelMapper
import com.itis.feature.events.impl.domain.mapper.EventsListUiModelMapper
import com.itis.feature.events.impl.domain.model.EventsListDomainModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCurrentEventsUseCaseTest {

    private lateinit var useCase: GetCurrentEventsUseCase
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var repository: EventsRepository

    @MockK
    lateinit var dataMapper: EventsListDomainModelMapper

    @MockK
    lateinit var domainMapper: EventsListUiModelMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = GetCurrentEventsUseCase(dataMapper, domainMapper, repository, testDispatcher)
    }

    @Test
    fun `invoke должен вернуть UI модель списка событий`() = runTest(testDispatcher) {
        val location = "test_location"
        val response = mockk<EventsListResponse>()
        val domainModel = mockk<EventsListDomainModel>()
        val uiModel = mockk<EventsListUiModel>()

        coEvery { repository.getEvents(location) } returns response
        coEvery { dataMapper.mapResponseToDomainModel(response) } returns domainModel
        coEvery { domainMapper.mapDomainToUiModel(domainModel) } returns uiModel

        val result = useCase(location)

        assertEquals(uiModel, result)
    }
}