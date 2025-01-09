package com.itis.feature.auth.impl.presentation.screens.splash

import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.data.storage.PreferencesImpl
import com.itis.feature.auth.impl.presentation.model.Prefs
import com.itis.feature.auth.impl.utils.UsersAuthRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val preferencesImpl: PreferencesImpl,
    private val router: UsersAuthRouter
): BaseViewModel() {
    private val _prefFlow = MutableStateFlow<Prefs>(Prefs(false, ""))
    val prefFlow :StateFlow<Prefs> get() = _prefFlow
    fun checkAuthStatus(){
        viewModelScope.launch {

            _prefFlow.emit(
                Prefs(preferencesImpl.getAutStatus(), preferencesImpl.getCurrentUserId())
            )
        }
    }
    fun setAuthStatus(flag:Boolean){
        preferencesImpl.saveAuthStatus(flag)
    }
    fun openInitial(){
        router.openInitialFromSplashScreen()
    }
//    fun openEventsScreen(){
//        router.openEventsScreenFromSplashScreen()
//    }
    fun openBiometricsAuthScreen(){
        router.openBiometricsAuthFromSplashScreen()
    }
}