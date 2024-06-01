package com.example.feature.profile.api.domain.repository

import com.example.feature.profile.api.domain.model.ProfileUserDataModel

interface ProfileRepository {
    suspend fun getUserProfileInfo(): ProfileUserDataModel
}