package com.example.feature.profile.impl.domain.usecase

import com.example.feature.profile.api.domain.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val repository: ProfileRepository
) {
    suspend operator fun invoke() {
        withContext(coroutineDispatcher) {
            repository.logoutUser()
        }
    }
}