package com.example.feature.profile.impl.domain.usecase

import android.net.Uri
import com.example.feature.profile.api.domain.repository.ProfileRepository
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

class UpdateUserProfilePictureUseCaseTest {

    private lateinit var useCase: UpdateUserProfilePictureUseCase
    private val testDispatcher = StandardTestDispatcher()

    @MockK(relaxed = true)
    lateinit var repository: ProfileRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = UpdateUserProfilePictureUseCase(testDispatcher, repository)
    }

    @Test
    fun `invoke должен загружать изображение и возвращать URL`() = runTest(testDispatcher) {
        val imageUri = mockk<Uri>()
        val imageUrl = "https://example.com/user/profile.jpg"

        coEvery { repository.uploadImageToFirebaseStorage(imageUri) } returns imageUrl

        val result = useCase(imageUri)

        assertEquals(imageUrl, result)
        coVerify { repository.uploadImageToFirebaseStorage(imageUri) }
    }
}