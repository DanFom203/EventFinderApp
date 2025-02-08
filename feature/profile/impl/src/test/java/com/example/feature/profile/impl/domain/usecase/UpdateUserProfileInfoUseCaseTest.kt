package com.example.feature.profile.impl.domain.usecase

import com.example.feature.profile.api.domain.repository.ProfileRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import kotlin.test.Test

class UpdateUserProfileInfoUseCaseTest {

    private lateinit var useCase: UpdateUserProfileInfoUseCase
    private val testDispatcher = StandardTestDispatcher()

    @MockK(relaxed = true)
    lateinit var repository: ProfileRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = UpdateUserProfileInfoUseCase(testDispatcher, repository)
    }

    @Test
    fun `invoke должен обновлять данные пользователя и возвращать результат`() = runTest(testDispatcher) {
        val username = "NewUser"
        val email = "newuser@mail.ru"
        val city = "NewCity"
        val updateResult = mapOf("username" to true, "email" to false, "city" to true)

        coEvery { repository.updateUserCredentials(username, email, city) } returns updateResult

        val result = useCase(username, email, city)

        assertEquals(updateResult, result)
        coVerify { repository.updateUserCredentials(username, email, city) }
    }
}