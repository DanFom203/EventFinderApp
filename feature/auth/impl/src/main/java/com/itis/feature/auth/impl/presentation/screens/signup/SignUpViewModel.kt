package com.itis.feature.auth.impl.presentation.screens.signup

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.utils.AsyncResult
import com.itis.feature.auth.impl.domain.usecases.SignUpUseCase
import com.itis.feature.auth.impl.presentation.model.SignUpForm
import com.itis.feature.auth.impl.presentation.model.UserUiModel
import com.itis.feature.auth.impl.utils.UsersAuthRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val router: UsersAuthRouter,
) : BaseViewModel() {
    private val _signUpFlow = MutableStateFlow<AsyncResult<UserUiModel>?>(null)
    val signUpFlow: StateFlow<AsyncResult<UserUiModel>?>
        get() = _signUpFlow

    fun signUp(username: String, email: String, password: String, city: String) {
        viewModelScope.launch {
            _signUpFlow.emit(AsyncResult.Loading)
            try {
                val result = signUpUseCase.invoke(
                    SignUpForm(
                        username = username,
                        email = email,
                        password = password,
                        city = city
                    )
                )
                _signUpFlow.emit(AsyncResult.Success(result))
            } catch (e: Exception) {
                _signUpFlow.emit(AsyncResult.Error(e))
            }
        }
    }

    fun openSignIn(){
        router.openSignInFromSignUp()
    }

}