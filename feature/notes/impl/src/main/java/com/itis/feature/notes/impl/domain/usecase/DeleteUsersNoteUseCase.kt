package com.itis.feature.notes.impl.domain.usecase

import com.itis.feature.notes.api.domain.repository.NotesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteUsersNoteUseCase @Inject constructor(
    private val repository: NotesRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(creationTime: Long) {
        withContext(coroutineDispatcher) {
            repository.deleteNoteByCreationTime(creationTime = creationTime)
        }
    }
}