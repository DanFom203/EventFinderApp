package com.itis.feature.auth.impl.presentation.screens.initial

import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.common.base.BaseFragment
import com.itis.common.di.FeatureUtils
import com.itis.feature.auth.api.di.AuthFeatureApi
import com.itis.feature.auth.impl.R
import com.itis.feature.auth.impl.databinding.FragmentInitialBinding
import com.itis.feature.auth.impl.di.AuthFeatureComponent

class InitialFragment: BaseFragment<InitialViewModel>(R.layout.fragment_initial) {

    private val viewBinding: FragmentInitialBinding by viewBinding(FragmentInitialBinding::bind)

    override fun initViews() {}

    override fun inject() {
        FeatureUtils.getFeature<AuthFeatureComponent>(this, AuthFeatureApi::class.java)
            .initialComponentFactory().create(this).inject(this)
    }

    override suspend fun subscribe(viewModel: InitialViewModel) {
        with(viewBinding) {
            signInBtn.setOnClickListener {
                viewModel.openSignIn()
            }
            signUpBtn.setOnClickListener {
                viewModel.openSignUp()
            }
        }
    }
}