package com.itis.feature.auth.impl.utils

interface UsersAuthRouter {
    fun openSignUpFromSignIn()

    fun openSignInFromSignUp()

    fun openSignUpFromInitial()
    fun openSignInFromInitial()

//    fun openSignUpFromSplashScreen()
    fun openInitialFromSplashScreen()
}