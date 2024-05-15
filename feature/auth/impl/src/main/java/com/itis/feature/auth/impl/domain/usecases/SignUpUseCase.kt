package com.itis.feature.auth.impl.domain.usecases

import com.itis.feature.auth.api.domain.repository.UserRepository
import com.itis.feature.auth.impl.domain.mapper.UserUiModelMapper
import com.itis.feature.auth.impl.presentation.model.SignUpForm
import com.itis.feature.auth.impl.presentation.model.UserUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: UserRepository,
    private val mapper: UserUiModelMapper
) {
    suspend operator fun invoke(signUpForm: SignUpForm): UserUiModel {
        return withContext(dispatcher) {
            val userData = repository.createUser(
                username = signUpForm.username,
                email = signUpForm.email,
                password = signUpForm.password,
                city = signUpForm.city
            )
            mapper.mapFromDataToUI(userData)
        }
    }
}