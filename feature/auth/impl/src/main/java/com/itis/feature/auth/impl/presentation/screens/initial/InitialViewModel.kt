package com.itis.feature.auth.impl.presentation.screens.initial

import com.itis.common.base.BaseViewModel
import com.itis.feature.auth.impl.utils.UsersAuthRouter

class InitialViewModel(
    private val router: UsersAuthRouter
) : BaseViewModel() {

    fun openSignUp(){
        router.openSignUpFromInitial()
    }

    fun openSignIn(){
        router.openSignInFromInitial()
    }
}