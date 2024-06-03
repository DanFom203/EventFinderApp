package com.example.feature.profile.impl.presentation.screens.change_credentials

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.feature.profile.api.di.ProfileFeatureApi
import com.example.feature.profile.impl.R
import com.example.feature.profile.impl.databinding.FragmentChangeCredentialsBinding
import com.example.feature.profile.impl.di.ProfileFeatureComponent
import com.itis.common.base.BaseFragment
import com.itis.common.data.exceptions.AuthException
import com.itis.common.di.FeatureUtils
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class ChangeCredentialsFragment : BaseFragment<ChangeCredentialsViewModel>(R.layout.fragment_change_credentials) {

    private val viewBinding: FragmentChangeCredentialsBinding by viewBinding(FragmentChangeCredentialsBinding::bind)

    override fun initViews() {
        with(viewBinding) {
            val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.cities_change_list,
                android.R.layout.simple_spinner_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            citySpinner.adapter = adapter
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<ProfileFeatureComponent>(this, ProfileFeatureApi::class.java)
            .changeCredentialsFactory()
            .create(this)
            .inject(this)
    }

    override suspend fun subscribe(viewModel: ChangeCredentialsViewModel) {
        with(viewModel) {
            with(viewBinding) {
                saveBtn.setOnClickListener {
                    passwordResetTil.error = null
                    newUsernameTil.error = null
                    newCityTil.error = null
                    changeCredentials(
                        username = newUsernameEt.text.toString(),
                        email = registeredEmailEt.text.toString(),
                        city = citySpinner.selectedItem.toString()
                    )
                }
                exitBtn.setOnClickListener {
                    openProfile()
                }
            }
            currentUpdateCredentialsFlow.observe { operationsSuccess ->
                if (operationsSuccess?.get(getString(R.string.password_reset)) == true) {
                    openProfile()
                    showToast(getString(R.string.reset_password_link_will_be_sent_to_your_email))
                }
                if (operationsSuccess?.get(getString(R.string.username_city_update)) == true) {
                    openProfile()
                    showToast(getString(R.string.success_update))
                }
            }

            lifecycleScope.launch {
                errorsChannel.consumeEach { error ->
                    val errorMessage = error.message ?: getString(R.string.unknown_error)
                    with(viewBinding) {
                        when (error) {

                            is AuthException.EmailInvalidCredentials -> {
                                passwordResetTil.error = error.message
                            }

                            is AuthException.UsernameInvalidCredentials -> {
                                newUsernameTil.error = error.message
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