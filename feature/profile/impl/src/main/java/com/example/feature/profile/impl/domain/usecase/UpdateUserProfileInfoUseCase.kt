package com.example.feature.profile.impl.domain.usecase

import com.example.feature.profile.api.domain.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateUserProfileInfoUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        username: String?,
        email: String?,
        city: String?
    ): Map<String, Boolean?> {
        return withContext(coroutineDispatcher) {
            repository.updateUserCredentials(
                username = username,
                email = email,
                city = city
            )
        }
    }
}