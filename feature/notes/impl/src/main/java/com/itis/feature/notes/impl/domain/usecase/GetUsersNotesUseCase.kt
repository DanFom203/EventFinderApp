package com.itis.feature.notes.impl.domain.usecase

import com.itis.feature.notes.api.domain.repository.NotesRepository
import com.itis.feature.notes.impl.data.mapper.NoteEntityDomainModelMapper
import com.itis.feature.notes.impl.domain.mapper.NoteUiModelMapper
import com.itis.feature.notes.api.presentation.model.NoteUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUsersNotesUseCase @Inject constructor(
    private val dataMapper: NoteEntityDomainModelMapper,
    private val domainMapper: NoteUiModelMapper,
    private val repository: NotesRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(userId: String): List<NoteUiModel> {
        return withContext(coroutineDispatcher) {
            repository.getNotesByUserId(userId = userId)?.mapNotNull {
                domainMapper.mapDomainToUiModel(dataMapper.mapDataToDomainModel(it))
            } ?: emptyList()
        }
    }
}