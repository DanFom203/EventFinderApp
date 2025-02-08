package com.itis.feature.auth.impl.presentation.screens.signin

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.data.storage.PreferencesImpl
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.common.utils.runCatching
import com.itis.feature.auth.impl.domain.usecases.SignInUseCase
import com.itis.feature.auth.api.presentation.model.SignInForm
import com.itis.feature.auth.api.presentation.model.UserUiModel
import com.itis.common.utils.CredentialsValidator
import com.itis.feature.auth.api.utils.UsersAuthRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val router: UsersAuthRouter,
    private val preferencesImpl: PreferencesImpl,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val credentialsValidator: CredentialsValidator
) : BaseViewModel() {
    private val _signInFlow = MutableStateFlow<UserUiModel?>(null)
    val signInFlow: StateFlow<UserUiModel?>
        get() = _signInFlow

    val errorsChannel = Channel<Throwable>()

    fun signIn(email:String?, password:String?) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                val verifiedEmail = credentialsValidator.verifyEmail(email)
                val verifiedPassword = credentialsValidator.verifyPassword(password)
                signInUseCase.invoke(SignInForm(verifiedEmail, verifiedPassword))
            }.onSuccess {
                _signInFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun openSignUp(){
        router.openSignUpFromSignIn()
    }

    fun openEventsScreen(){
        router.openEventsScreenFromSignIn()
    }

    fun initializeUser(){
        preferencesImpl.saveAuthStatus(true)
    }
}