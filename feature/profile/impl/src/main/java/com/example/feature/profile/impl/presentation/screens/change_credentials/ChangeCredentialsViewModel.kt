package com.example.feature.profile.impl.presentation.screens.change_credentials

import androidx.lifecycle.viewModelScope
import com.example.feature.profile.impl.domain.usecase.UpdateUserProfileInfoUseCase
import com.example.feature.profile.api.utils.ProfileFeatureRouter
import com.itis.common.base.BaseViewModel
import com.itis.common.utils.CityFormatter
import com.itis.common.utils.CredentialsValidator
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.common.utils.runCatching
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChangeCredentialsViewModel(
    private val updateUserProfileInfoUseCase: UpdateUserProfileInfoUseCase,
    private val router: ProfileFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val credentialsValidator: CredentialsValidator,
    private val cityFormatter: CityFormatter
) : BaseViewModel() {
    private val _currentUpdateCredentialsFlow = MutableStateFlow<Map<String, Boolean?>?>(null)
    val currentUpdateCredentialsFlow: StateFlow<Map<String, Boolean?>?>
        get() = _currentUpdateCredentialsFlow

    val errorsChannel = Channel<Throwable>()

    fun changeCredentials(username: String?, email: String?, city: String?) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                credentialsValidator.verifyCredentials(
                    username = username,
                    email = email,
                    city = city
                )
                var verifiedEmail = email
                var verifiedUsername = username
                var cityAbbreviation = city

                if (username != null) {
                    verifiedUsername = credentialsValidator.verifyUsernameUpdate(username)
                }
                if (city != null) {
                    cityAbbreviation = cityFormatter.cityToAbbreviation(city)
                }
                if (email != null) {
                    verifiedEmail = credentialsValidator.verifyEmailUpdate(email)
                }
                updateUserProfileInfoUseCase.invoke(
                    username = verifiedUsername,
                    email = verifiedEmail,
                    city = cityAbbreviation
                )
            }.onSuccess {
                _currentUpdateCredentialsFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun openProfile() {
        router.openProfileScreenFromChangeCredentialsScreen()
    }
}