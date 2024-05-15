package com.itis.feature.auth.impl.domain.mapper

import com.itis.feature.auth.api.domain.model.User
import com.itis.feature.auth.impl.presentation.model.UserUiModel
import javax.inject.Inject

class UserUiModelMapper @Inject constructor() {
    fun mapFromDataToUI(user: User): UserUiModel {
        return UserUiModel(
            name = user.username,
            email = user.email
        )
    }
}