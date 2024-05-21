package com.itis.feature.auth.impl.presentation.screens.signin

import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.common.base.BaseFragment
import com.itis.common.di.FeatureUtils
import com.itis.common.utils.AsyncResult
import com.itis.feature.auth.api.di.AuthFeatureApi
import com.itis.feature.auth.impl.R
import com.itis.feature.auth.impl.databinding.FragmentSignInBinding
import com.itis.feature.auth.impl.di.AuthFeatureComponent

class SignInFragment : BaseFragment<SignInViewModel>(R.layout.fragment_sign_in) {
    private val viewBinding: FragmentSignInBinding by viewBinding(FragmentSignInBinding::bind)

    override fun initViews() {}

    override fun inject() {
        FeatureUtils.getFeature<AuthFeatureComponent>(this, AuthFeatureApi::class.java)
            .signInComponentFactory().create(this).inject(this)
    }

    override suspend fun subscribe(viewModel: SignInViewModel) {
        with(viewBinding) {
            signInBtn.setOnClickListener {
                viewModel.signIn(
                    email = emailEt.text.toString(),
                    password = passwordEt.text.toString()
                )
            }
            noAccountTv.setOnClickListener {
                viewModel.openSignUp()
            }
        }

        viewModel.signInFlow.collect { result ->
            when (result) {
                is AsyncResult.Success -> {
                    viewModel.initializeUser()
                    viewModel.openEventsScreen()
                }

                is AsyncResult.Error -> {
                    viewModel.errorHandling(result.getExceptionOrNull()!!)
                }

                else -> {
                }
            }
        }
        viewModel.errorFlow.collect {
            it?.message?.let { it1 -> showToast(it1) }
        }
    }
}