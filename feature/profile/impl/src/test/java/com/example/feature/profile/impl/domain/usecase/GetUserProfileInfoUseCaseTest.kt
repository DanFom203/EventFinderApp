package com.example.feature.profile.impl.domain.usecase

import com.example.feature.profile.api.domain.model.ProfileUserDataModel
import com.example.feature.profile.api.domain.repository.ProfileRepository
import com.example.feature.profile.impl.data.mapper.UserProfileDomainModelMapper
import com.example.feature.profile.impl.domain.mapper.UserProfileUiModelMapper
import com.example.feature.profile.api.presentation.model.ProfileUserUiModel
import com.example.feature.profile.impl.domain.model.ProfileUserDomainModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUserProfileInfoUseCaseTest {

    private lateinit var useCase: GetUserProfileInfoUseCase
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var repository: ProfileRepository

    @MockK
    lateinit var dataMapper: UserProfileDomainModelMapper

    @MockK
    lateinit var domainMapper: UserProfileUiModelMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = GetUserProfileInfoUseCase(testDispatcher, repository, dataMapper, domainMapper)
    }

    @Test
    fun `invoke должен возвращать профиль пользователя`() = runTest(testDispatcher) {
        val domainModel = mockk<ProfileUserDomainModel>()
        val dataModel = mockk<ProfileUserDataModel>()
        val uiModel = mockk<ProfileUserUiModel>()

        coEvery { repository.getUserProfileInfo() } returns dataModel
        coEvery { dataMapper.mapDataToDomainModel(dataModel) } returns domainModel
        coEvery { domainMapper.mapDomainToUiModel(domainModel) } returns uiModel

        val result = useCase()

        assertEquals(uiModel, result)
        coVerify { repository.getUserProfileInfo() }
        coVerify { dataMapper.mapDataToDomainModel(dataModel) }
        coVerify { domainMapper.mapDomainToUiModel(domainModel) }
    }
}