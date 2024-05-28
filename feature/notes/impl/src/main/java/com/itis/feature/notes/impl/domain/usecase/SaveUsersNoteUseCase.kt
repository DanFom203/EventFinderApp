package com.itis.feature.notes.impl.domain.usecase

import com.itis.feature.notes.api.domain.repository.NotesRepository
import com.itis.feature.notes.impl.presentation.mapper.NoteDataModelMapper
import com.itis.feature.notes.impl.presentation.model.NoteUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveUsersNoteUseCase @Inject constructor(
    private val mapper: NoteDataModelMapper,
    private val repository: NotesRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(noteUiModel: NoteUiModel) {
        withContext(coroutineDispatcher) {
            mapper.mapUiToDataModel(noteUiModel)
                ?.let { repository.saveNoteToDatabase(noteEntity = it) }
        }
    }
}