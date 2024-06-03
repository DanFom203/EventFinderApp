package com.example.feature.profile.api.domain.repository

import android.net.Uri
import com.example.feature.profile.api.domain.model.ProfileUserDataModel

interface ProfileRepository {
    suspend fun getUserProfileInfo(): ProfileUserDataModel

    suspend fun updateUserCredentials(
        username: String?,
        email: String?,
        city: String?
    ): Map<String, Boolean?>

    suspend fun logoutUser()

    suspend fun uploadImageToFirebaseStorage(imageUri: Uri): String
}