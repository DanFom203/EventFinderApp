package com.itis.feature.notes.impl.domain.usecase

import com.itis.feature.notes.api.domain.repository.NotesRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteUsersNoteUseCaseTest {

    private lateinit var useCase: DeleteUsersNoteUseCase
    private val testDispatcher = StandardTestDispatcher()

    @MockK(relaxed = true)
    lateinit var notesRepository: NotesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = DeleteUsersNoteUseCase(notesRepository, testDispatcher)
    }

    @Test
    fun `invoke должен вызывать метод удаления по времени создания заметки`() = runTest(testDispatcher) {
        val creationTime = 123456789L

        useCase(creationTime)

        coVerify { notesRepository.deleteNoteByCreationTime(creationTime) }
    }
}