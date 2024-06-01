package com.itis.feature.auth.impl.presentation.screens.signin

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.common.base.BaseFragment
import com.itis.common.data.exceptions.AuthException
import com.itis.common.di.FeatureUtils
import com.itis.feature.auth.api.di.AuthFeatureApi
import com.itis.feature.auth.impl.R
import com.itis.feature.auth.impl.databinding.FragmentSignInBinding
import com.itis.feature.auth.impl.di.AuthFeatureComponent
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class SignInFragment : BaseFragment<SignInViewModel>(R.layout.fragment_sign_in) {
    private val viewBinding: FragmentSignInBinding by viewBinding(FragmentSignInBinding::bind)

    override fun initViews() {
        with(viewBinding) {
            noAccountTv.setOnClickListener {
                viewModel.openSignUp()
            }
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<AuthFeatureComponent>(this, AuthFeatureApi::class.java)
            .signInComponentFactory()
            .create(this)
            .inject(this)
    }

    override suspend fun subscribe(viewModel: SignInViewModel) {
        with(viewModel) {
            with(viewBinding) {
                signInBtn.setOnClickListener {
                    emailTil.error = null
                    passwordTil.error = null
                    viewModel.signIn(
                        email = emailEt.text.toString(),
                        password = passwordEt.text.toString()
                    )
                }
            }

            signInFlow.observe { userUiModel ->
                if (userUiModel != null) {
                    initializeUser()
                    openEventsScreen()
                }
            }

            lifecycleScope.launch {
                errorsChannel.consumeEach { error ->
                    val errorMessage = error.message ?: getString(R.string.unknown_error)
                    with(viewBinding) {
                        when (error) {

                            is AuthException.EmptyEmailField, is AuthException.EmailInvalidCredentials -> {
                                emailTil.error = error.message
                            }

                            is AuthException.EmptyPasswordField, is AuthException.PasswordInvalidCredentials -> {
                                passwordTil.error = error.message
                            }

                            else -> Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }
}