package com.itis.eventfinderapp.navigation

import androidx.navigation.NavController
import com.itis.eventfinderapp.R
import com.itis.feature.auth.impl.utils.UsersAuthRouter

class Navigator : UsersAuthRouter {

    private var navController: NavController? = null

    fun attachNavController(navController: NavController, graph: Int) {
        navController.setGraph(graph)
        this.navController = navController
    }

    fun detachNavController(navController: NavController) {
        if (this.navController == navController) {
            this.navController = null
        }
    }
    override fun openSignUpFromSignIn() {
        navController?.navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    override fun openSignInFromSignUp() {
        navController?.navigate(R.id.action_signUpFragment_to_signInFragment)
    }

    override fun openSignUpFromInitial() {
        navController?.navigate(R.id.action_initialFragment_to_signUpFragment)
    }

    override fun openSignInFromInitial() {
        navController?.navigate(R.id.action_initialFragment_to_signInFragment)
    }

    override fun openInitialFromSplashScreen() {
        navController?.navigate(R.id.action_splashScreenFragment_to_initialFragment)
    }
}