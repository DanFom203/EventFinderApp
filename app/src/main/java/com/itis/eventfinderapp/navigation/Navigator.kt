package com.itis.eventfinderapp.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.feature.profile.impl.utils.ProfileFeatureRouter
import com.itis.eventfinderapp.R
import com.itis.eventfinderapp.di.main.MainActivity
import com.itis.feature.auth.impl.utils.UsersAuthRouter
import com.itis.feature.biometrics.impl.utils.BiometricsAuthRouter
import com.itis.feature.events.impl.presentation.model.EventUiModel
import com.itis.feature.events.impl.presentation.screens.event_info.EventInfoFragment
import com.itis.feature.events.impl.utils.EventsFeatureRouter
import com.itis.feature.notes.impl.utils.NotesFeatureRouter

class Navigator : UsersAuthRouter, EventsFeatureRouter, NotesFeatureRouter, ProfileFeatureRouter, BiometricsAuthRouter {

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
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.splashScreenFragment, true)
            .build()
        navController?.navigate(
            R.id.action_splashScreenFragment_to_initialFragment,
            null,
            navOptions
        )
    }

    override fun openEventsScreenFromSignIn() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.signInFragment, true)
            .build()
        navController?.navigate(
            R.id.action_signInFragment_to_eventsFragment,
            null,
            navOptions
        )
    }

//    override fun openEventsScreenFromSplashScreen() {
//        val navOptions = NavOptions.Builder()
//            .setPopUpTo(R.id.splashScreenFragment, true)
//            .build()
//        navController?.navigate(
//            R.id.action_splashScreenFragment_to_eventsFragment,
//            null,
//            navOptions
//        )
//    }

    override fun openBiometricsAuthFromSplashScreen() {
        navController?.navigate(R.id.action_splashScreenFragment_to_biometricsAuthFragment)
    }

    override fun openEventInfoScreenFromEventsScreen(eventUiModel: EventUiModel) {
        navController?.navigate(
            R.id.action_eventsFragment_to_eventInfoFragment,
            EventInfoFragment.createBundle(
                eventId = eventUiModel.id
            )
        )
    }

    override fun openEventInfoScreenFromFavouriteEventsScreen(eventUiModel: EventUiModel) {
        navController?.navigate(
            R.id.action_favouriteEventsFragment_to_eventInfoFragment,
            EventInfoFragment.createBundle(
                eventId = eventUiModel.id
            )
        )
    }

    override fun openEventsScreenFromEventInfoScreen() {
        navController?.popBackStack()
    }

    override fun openProfileScreenFromFavouriteEventsScreen() {
        navController?.popBackStack()
    }

    override fun openAddNoteScreenFromNotesScreen() {
        navController?.navigate(R.id.action_notesFragment_to_addNoteFragment)
    }

    override fun openNotesScreenFromAddNotesScreen() {
        navController?.popBackStack()
    }

    override fun openChangeCredentialsScreenFromProfileScreen() {
        navController?.navigate(R.id.action_profileFragment_to_changeCredentialsFragment)
    }

    override fun openProfileScreenFromChangeCredentialsScreen() {
        navController?.popBackStack()
    }

    override fun openInitialScreenFromProfileScreen() {
        val context = navController?.context ?: return
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    override fun openFavouriteEventsFromProfileScreen() {
        navController?.navigate(R.id.action_profileFragment_to_favouriteEventsFragment)
    }

    override fun openEventsScreenFromBiometrics() {
        navController?.navigate(R.id.action_biometricsAuthFragment_to_eventsFragment)
    }
}