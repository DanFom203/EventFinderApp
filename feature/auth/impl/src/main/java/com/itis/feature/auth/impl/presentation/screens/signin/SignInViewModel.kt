package com.itis.feature.auth.impl.presentation.screens.signin

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.storage.PreferencesImpl
import com.itis.common.utils.AsyncResult
import com.itis.feature.auth.impl.domain.usecases.SignInUseCase
import com.itis.feature.auth.impl.presentation.model.SignInForm
import com.itis.feature.auth.impl.presentation.model.UserUiModel
import com.itis.feature.auth.impl.utils.UsersAuthRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val router: UsersAuthRouter,
    private val preferencesImpl: PreferencesImpl,
) : BaseViewModel() {
    private val _signInFlow = MutableStateFlow<AsyncResult<UserUiModel>?>(null)
    val signInFlow: StateFlow<AsyncResult<UserUiModel>?>
        get() = _signInFlow

    fun signIn(email:String,password:String) {
        viewModelScope.launch {
            _signInFlow.emit(AsyncResult.Loading)
            try {
                val result = signInUseCase.invoke(SignInForm(email,password))
                _signInFlow.emit(AsyncResult.Success(result))
            } catch (e:Exception){
                _signInFlow.emit(AsyncResult.Error(e))
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