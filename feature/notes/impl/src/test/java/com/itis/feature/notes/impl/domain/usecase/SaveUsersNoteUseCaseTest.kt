package com.itis.feature.notes.impl.domain.usecase

import com.itis.common.data.local.db.entity.NoteEntity
import com.itis.feature.notes.api.domain.repository.NotesRepository
import com.itis.feature.notes.api.presentation.model.NoteUiModel
import com.itis.feature.notes.impl.presentation.mapper.NoteDataModelMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

class SaveUsersNoteUseCaseTest {

    private lateinit var saveUsersNoteUseCase: SaveUsersNoteUseCase
    private val testDispatcher = StandardTestDispatcher()

    @MockK(relaxed = true)
    lateinit var notesRepository: NotesRepository

    @MockK
    lateinit var mapper: NoteDataModelMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        saveUsersNoteUseCase = SaveUsersNoteUseCase(mapper, notesRepository, testDispatcher)
    }

    @Test
    fun `invoke должен сохранять записку в репозиторий`() = runTest(testDispatcher) {
        val uiModel = mockk<NoteUiModel>()
        val dataModel = mockk<NoteEntity>()

        coEvery { mapper.mapUiToDataModel(uiModel) } returns dataModel

        saveUsersNoteUseCase(uiModel)

        coVerify { notesRepository.saveNoteToDatabase(dataModel) }
    }
}