package com.example.feature.profile.api.utils

interface ProfileFeatureRouter {
    fun openChangeCredentialsScreenFromProfileScreen()

    fun openProfileScreenFromChangeCredentialsScreen()

    fun openInitialScreenFromProfileScreen()

    fun openFavouriteEventsFromProfileScreen()
}