package com.itis.feature.notes.impl.domain.usecase

import com.itis.common.data.local.db.entity.NoteEntity
import com.itis.feature.notes.api.domain.repository.NotesRepository
import com.itis.feature.notes.impl.data.mapper.NoteEntityDomainModelMapper
import com.itis.feature.notes.impl.domain.mapper.NoteUiModelMapper
import com.itis.feature.notes.impl.domain.model.NoteDomainModel
import com.itis.feature.notes.impl.presentation.model.NoteUiModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUsersNotesUseCaseTest {

    private lateinit var useCase: GetUsersNotesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var notesRepository: NotesRepository

    @MockK
    lateinit var domainMapper: NoteUiModelMapper

    @MockK
    lateinit var dataMapper: NoteEntityDomainModelMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = GetUsersNotesUseCase(dataMapper, domainMapper, notesRepository, testDispatcher)
    }

    @Test
    fun `invoke должен возвращать список заметок`(): Unit = runTest(testDispatcher) {
        val userId = "test_user"
        val noteEntity = mockk<NoteEntity>()
        val domainModel = mockk<NoteDomainModel>()
        val uiModel = mockk<NoteUiModel>()

        coEvery { notesRepository.getNotesByUserId(userId) } returns listOf(noteEntity)
        coEvery { dataMapper.mapDataToDomainModel(noteEntity) } returns domainModel
        coEvery { domainMapper.mapDomainToUiModel(domainModel) } returns uiModel

        val result = useCase(userId)

        assertEquals(listOf(uiModel), result)
    }
}