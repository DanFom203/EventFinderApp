package com.example.feature.profile.impl.domain.mapper

import com.example.feature.profile.impl.domain.model.ProfileUserDomainModel
import com.example.feature.profile.api.presentation.model.ProfileUserUiModel
import javax.inject.Inject

class UserProfileUiModelMapper @Inject constructor() {

    fun mapDomainToUiModel(input: ProfileUserDomainModel?): ProfileUserUiModel? {
        return input?.let {
            ProfileUserUiModel(
                userId = it.userId,
                avatar = it.avatar,
                username = it.username,
                email = it.email,
                city = it.city
            )
        }
    }
}