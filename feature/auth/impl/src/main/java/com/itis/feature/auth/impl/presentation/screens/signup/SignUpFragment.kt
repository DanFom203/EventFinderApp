package com.itis.feature.auth.impl.presentation.screens.signup

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.common.base.BaseFragment
import com.itis.common.data.exceptions.AuthException
import com.itis.common.di.FeatureUtils
import com.itis.feature.auth.api.di.AuthFeatureApi
import com.itis.feature.auth.impl.R
import com.itis.feature.auth.impl.databinding.FragmentSignUpBinding
import com.itis.feature.auth.impl.di.AuthFeatureComponent
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class SignUpFragment : BaseFragment<SignUpViewModel>(R.layout.fragment_sign_up) {

    private val viewBinding: FragmentSignUpBinding by viewBinding(FragmentSignUpBinding::bind)

    override fun initViews() {
        with(viewBinding) {
            val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.cities,
                android.R.layout.simple_spinner_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            citySpinner.adapter = adapter

            alreadyHaveAnAccountTv.setOnClickListener {
                viewModel.openSignIn()
            }
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<AuthFeatureComponent>(this, AuthFeatureApi::class.java)
            .signUpComponentFactory().create(this).inject(this)
    }

    override suspend fun subscribe(viewModel: SignUpViewModel) {
        with(viewBinding) {
            signUpBtn.setOnClickListener {
                newEmailTil.error = null
                newPasswordTil.error = null
                usernameTil.error = null
                cityTil.error = null
                viewModel.signUp(
                    username = usernameEt.text.toString(),
                    email = newEmailEt.text.toString(),
                    password = newPasswordEt.text.toString(),
                    city = citySpinner.selectedItem.toString()
                )
            }
        }

        with(viewModel) {
            signUpFlow.observe { userUiModel ->
                if (userUiModel != null) {
                    openSignIn()
                }
            }

            lifecycleScope.launch {
                errorsChannel.consumeEach { error ->
                    val errorMessage = error.message ?: getString(R.string.unknown_error)
                    with(viewBinding) {
                        when (error) {

                            is AuthException.EmptyEmailField, is AuthException.EmailInvalidCredentials -> {
                                newEmailTil.error = error.message
                            }

                            is AuthException.EmptyPasswordField, is AuthException.PasswordInvalidCredentials -> {
                                newPasswordTil.error = error.message
                            }

                            is AuthException.EmptyUsernameField, is AuthException.UsernameInvalidCredentials -> {
                                usernameTil.error = error.message
                            }

                            is AuthException.EmptyCityField -> {
                                cityTil.error = error.message
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