package com.itis.feature.auth.impl.domain.usecases

import com.itis.feature.auth.api.domain.repository.UserRepository
import com.itis.feature.auth.impl.domain.mapper.UserUiModelMapper
import com.itis.feature.auth.api.presentation.model.SignInForm
import com.itis.feature.auth.api.presentation.model.UserUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: UserRepository,
    private val mapper: UserUiModelMapper
) {
    suspend operator fun invoke(signInForm: SignInForm): UserUiModel {
        return withContext(dispatcher) {
            val userData = repository.signIn(
                email = signInForm.email,
                password = signInForm.password,
            )
            mapper.mapFromDataToUI(userData)
        }
    }
}