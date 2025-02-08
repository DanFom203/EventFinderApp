package com.itis.feature.auth.api.utils

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