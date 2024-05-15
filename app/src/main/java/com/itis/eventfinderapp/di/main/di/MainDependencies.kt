package com.itis.eventfinderapp.di.main.di

import com.itis.eventfinderapp.di.deps.ComponentDependencies
import com.itis.eventfinderapp.navigation.Navigator

interface MainDependencies : ComponentDependencies {

    fun navigator(): Navigator
}