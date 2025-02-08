package com.itis.feature.events.impl.domain.usecase

import com.itis.feature.events.api.domain.repository.EventsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExistsInFavouritesUseCaseTest {

    private lateinit var useCase: ExistsInFavouritesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var repository: EventsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = ExistsInFavouritesUseCase(repository, testDispatcher)
    }

    @Test
    fun `invoke должен вернуть факт существовавания в репозитории`() = runTest(testDispatcher) {
        val eventId = 123
        coEvery { repository.existsInFirestoreDb(eventId) } returns true

        val result = useCase(eventId)

        assertEquals(true, result)
    }
}