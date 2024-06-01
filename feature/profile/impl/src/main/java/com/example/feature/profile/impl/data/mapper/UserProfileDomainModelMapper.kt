package com.example.feature.profile.impl.data.mapper

import com.example.feature.profile.api.domain.model.ProfileUserDataModel
import com.example.feature.profile.impl.domain.model.ProfileUserDomainModel
import javax.inject.Inject

class UserProfileDomainModelMapper @Inject constructor() {

    fun mapDataToDomainModel(input: ProfileUserDataModel?): ProfileUserDomainModel? {
        return input?.let {
            ProfileUserDomainModel(
                userId = it.userId,
                avatar = it.avatar,
                username = it.username,
                email = it.email,
                city = it.city
            )
        }
    }
}