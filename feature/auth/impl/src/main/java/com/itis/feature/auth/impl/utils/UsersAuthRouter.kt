package com.itis.feature.auth.impl.utils

interface UsersAuthRouter {
    fun openSignUpFromSignIn()

    fun openSignInFromSignUp()

    fun openSignUpFromInitial()

    fun openSignInFromInitial()

    fun openInitialFromSplashScreen()

    fun openEventsScreenFromSignIn()

//    fun openEventsScreenFromSplashScreen()

    fun openBiometricsAuthFromSplashScreen()
}