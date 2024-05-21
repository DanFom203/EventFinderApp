package com.itis.eventfinderapp.navigation

import androidx.navigation.NavController
import com.itis.eventfinderapp.R
import com.itis.feature.auth.impl.utils.UsersAuthRouter
import com.itis.feature.events.impl.presentation.model.EventUiModel
import com.itis.feature.events.impl.presentation.screens.event_info.EventInfoFragment
import com.itis.feature.events.impl.utils.EventsFeatureRouter

class Navigator : UsersAuthRouter, EventsFeatureRouter {

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

    override fun openEventsScreenFromSignIn() {
        navController?.navigate(R.id.action_signInFragment_to_eventsFragment)
    }

    override fun openEventsScreenFromSplashScreen() {
        navController?.navigate(R.id.action_splashScreenFragment_to_eventsFragment)
    }

    override fun openEventInfoScreenFromEventsScreen(eventUiModel: EventUiModel) {
        navController?.navigate(
            R.id.action_eventsFragment_to_eventInfoFragment,
            EventInfoFragment.createBundle(
                eventId = eventUiModel.id
            )
        )
    }
}