package com.itis.feature.events.impl.domain.usecase

import com.itis.feature.events.api.domain.repository.EventsRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteFromFavouritesUseCaseTest {

    private lateinit var useCase: DeleteFromFavouritesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @MockK(relaxed = true)
    lateinit var repository: EventsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = DeleteFromFavouritesUseCase(repository, testDispatcher)
    }

    @Test
    fun `invoke должен вызвать deleteFromFirestoreDb с корректным eventId`() = runTest(testDispatcher) {
        val eventId = 123

        useCase(eventId)

        coVerify { repository.deleteFromFirestoreDb(eventId) }
    }
}
