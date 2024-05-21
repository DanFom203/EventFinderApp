package com.itis.feature.auth.impl.presentation.screens.splash

import com.itis.common.base.BaseFragment
import com.itis.common.di.FeatureUtils
import com.itis.feature.auth.api.di.AuthFeatureApi
import com.itis.feature.auth.impl.R
import com.itis.feature.auth.impl.di.AuthFeatureComponent

class SplashScreenFragment: BaseFragment<SplashScreenViewModel>(R.layout.fragment_splash_screen) {
    override fun initViews() {
        viewModel.checkAuthStatus()
    }

    override fun inject() {
        FeatureUtils.getFeature<AuthFeatureComponent>(this, AuthFeatureApi::class.java)
            .splashScreenFactory().create(this).inject(this)
    }
    override suspend fun subscribe(viewModel: SplashScreenViewModel) {
        viewModel.prefFlow.collect { result ->
            if (result){
                viewModel.openEventsScreen()
                viewModel.setAuthStatus(false)
                viewModel.setAuthStatus(true)
            } else {
                viewModel.openInitial()
            }
        }
    }

}