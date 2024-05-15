package com.itis.feature.auth.impl.presentation.screens.signup

import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.common.base.BaseFragment
import com.itis.common.di.FeatureUtils
import com.itis.common.utils.AsyncResult
import com.itis.feature.auth.api.di.AuthFeatureApi
import com.itis.feature.auth.impl.R
import com.itis.feature.auth.impl.databinding.FragmentSignUpBinding
import com.itis.feature.auth.impl.di.AuthFeatureComponent

class SignUpFragment : BaseFragment<SignUpViewModel>(R.layout.fragment_sign_up) {

    private val viewBinding: FragmentSignUpBinding by viewBinding(FragmentSignUpBinding::bind)

    override fun initViews() {}

    override fun inject() {
        FeatureUtils.getFeature<AuthFeatureComponent>(this, AuthFeatureApi::class.java)
            .signUpComponentFactory().create(this).inject(this)
    }

    override suspend fun subscribe(viewModel: SignUpViewModel) {
        with(viewBinding) {
            signUpBtn.setOnClickListener {
                viewModel.signUp(
                    username = usernameEt.text.toString(),
                    email = newEmailEt.text.toString(),
                    password = newPasswordEt.text.toString(),
                    city = cityEt.text.toString()
                )
            }
            alreadyHaveAnAccountTv.setOnClickListener {
                viewModel.openSignIn()
            }
        }

        viewModel.signUpFlow.collect { result ->
            when (result) {
                is AsyncResult.Success -> {
                    viewModel.initializeUser()
//                    viewModel.openPrediction()
                }

                is AsyncResult.Error -> {
                    viewModel.errorHandling(result.getExceptionOrNull()!!)
                }
                else -> {
                }
            }
        }
        viewModel.errorFlow.collect{
            it?.message?.let { it1 -> showToast(it1) }
        }
    }
}