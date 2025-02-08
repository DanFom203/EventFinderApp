package com.example.feature.profile.impl.domain.usecase

import com.example.feature.profile.api.domain.repository.ProfileRepository
import com.example.feature.profile.impl.data.mapper.UserProfileDomainModelMapper
import com.example.feature.profile.impl.domain.mapper.UserProfileUiModelMapper
import com.example.feature.profile.api.presentation.model.ProfileUserUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserProfileInfoUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val repository: ProfileRepository,
    private val dataMapper: UserProfileDomainModelMapper,
    private val domainMapper: UserProfileUiModelMapper
) {
    suspend operator fun invoke(): ProfileUserUiModel {
        return withContext(coroutineDispatcher) {
            dataMapper.mapDataToDomainModel(repository.getUserProfileInfo())
                ?.let { domainMapper.mapDomainToUiModel(it) }!!
        }
    }
}