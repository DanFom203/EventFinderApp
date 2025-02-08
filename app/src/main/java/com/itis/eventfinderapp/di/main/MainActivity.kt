package com.itis.eventfinderapp.di.main

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.itis.common.base.BaseActivity
import com.itis.eventfinderapp.R
import com.itis.eventfinderapp.di.deps.findComponentDependencies
import com.itis.eventfinderapp.di.main.di.MainComponent
import com.itis.eventfinderapp.navigation.Navigator
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>(){

    private lateinit var analytics: FirebaseAnalytics

    @Inject lateinit var navigator: Navigator

    private var controller: NavController? = null

    override fun inject() {
        MainComponent.init(this, findComponentDependencies())
            .inject(this)
    }

    override fun layoutResource(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        // Obtain the FirebaseAnalytics instance.
        analytics = Firebase.analytics

        controller =
            (supportFragmentManager.findFragmentById(R.id.main_activity_container) as NavHostFragment)
                .navController
        navigator.attachNavController(this.controller!!, R.navigation.nav_graph)
        findViewById<BottomNavigationView>(R.id.menu_bnv).apply {
            setupWithNavController(this@MainActivity.controller!!)
        }

        controller!!.addOnDestinationChangedListener { _, destination, _ ->
            handleBottomNavigationViewVisibility(destination)
        }
    }

    private fun handleBottomNavigationViewVisibility(destination: NavDestination) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.menu_bnv)

        val shouldShowBottomNav = when (destination.id) {
            R.id.eventInfoFragment,
            R.id.splashScreenFragment,
            R.id.initialFragment,
            R.id.signInFragment,
            R.id.signUpFragment,
            R.id.addNoteFragment,
            R.id.changeCredentialsFragment,
            R.id.favouriteEventsFragment,
            R.id.biometricsAuthFragment -> false
            else -> true
        }

        bottomNavigationView.visibility = if (shouldShowBottomNav) {
            View.VISIBLE
        } else {
            View.GONE
        }

    }

    override suspend fun subscribe(viewModel: MainViewModel) { }

    override fun onDestroy() {
        super.onDestroy()
        controller?.let {
            navigator.detachNavController(it)
        }
    }

}