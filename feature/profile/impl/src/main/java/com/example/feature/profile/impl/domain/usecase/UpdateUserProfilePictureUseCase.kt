package com.example.feature.profile.impl.domain.usecase

import android.net.Uri
import com.example.feature.profile.api.domain.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateUserProfilePictureUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(imageUri: Uri): String {
        return withContext(coroutineDispatcher) {
            repository.uploadImageToFirebaseStorage(imageUri = imageUri)
        }
    }
}