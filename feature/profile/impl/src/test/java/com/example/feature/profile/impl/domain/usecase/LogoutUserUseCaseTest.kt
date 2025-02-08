package com.example.feature.profile.impl.domain.usecase

import com.example.feature.profile.api.domain.repository.ProfileRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

class LogoutUserUseCaseTest {

    private lateinit var useCase: LogoutUserUseCase
    private val testDispatcher = StandardTestDispatcher()

    @MockK(relaxed = true)
    lateinit var repository: ProfileRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = LogoutUserUseCase(testDispatcher, repository)
    }

    @Test
    fun `invoke должен вызывать logoutUser у репозитория`() = runTest(testDispatcher) {
        useCase()

        coVerify { repository.logoutUser() }
    }
}