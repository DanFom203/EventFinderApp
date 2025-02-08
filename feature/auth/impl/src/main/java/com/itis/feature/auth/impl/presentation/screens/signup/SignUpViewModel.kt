package com.itis.feature.auth.impl.presentation.screens.signup

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.utils.CityFormatter
import com.itis.common.utils.CredentialsValidator
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.common.utils.runCatching
import com.itis.feature.auth.impl.domain.usecases.SignUpUseCase
import com.itis.feature.auth.api.presentation.model.SignUpForm
import com.itis.feature.auth.api.presentation.model.UserUiModel
import com.itis.feature.auth.api.utils.UsersAuthRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val router: UsersAuthRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val credentialsValidator: CredentialsValidator,
    private val cityFormatter: CityFormatter
) : BaseViewModel() {
    private val _signUpFlow = MutableStateFlow<UserUiModel?>(null)
    val signUpFlow: StateFlow<UserUiModel?>
        get() = _signUpFlow

    val errorsChannel = Channel<Throwable>()

    fun signUp(username: String?, email: String?, password: String?, city: String?) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                val verifiedEmail = credentialsValidator.verifyEmail(email)
                val verifiedPassword = credentialsValidator.verifyPassword(password)
                val verifiedUsername = credentialsValidator.verifyUsername(username)
                val verifiedCity = credentialsValidator.verifyCity(city)
                signUpUseCase.invoke(
                    SignUpForm(
                        username = verifiedUsername,
                        email = verifiedEmail,
                        password = verifiedPassword,
                        city = cityFormatter.cityToAbbreviation(verifiedCity)!!
                    )
                )
            }.onSuccess {
                _signUpFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun openSignIn(){
        router.openSignInFromSignUp()
    }

}