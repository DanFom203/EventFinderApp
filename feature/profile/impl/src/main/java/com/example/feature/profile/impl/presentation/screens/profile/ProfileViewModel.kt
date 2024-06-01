package com.example.feature.profile.impl.presentation.screens.profile

import com.example.feature.profile.impl.domain.usecase.GetUserProfileInfoUseCase
import com.example.feature.profile.impl.presentation.model.ProfileUserUiModel
import com.example.feature.profile.impl.utils.ProfileFeatureRouter
import com.itis.common.base.BaseViewModel
import com.itis.common.utils.CityFormatter
import com.itis.common.utils.CredentialsValidator
import com.itis.common.utils.ExceptionHandlerDelegate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel(
    private val getUserProfileInfoUseCase: GetUserProfileInfoUseCase,
    private val router: ProfileFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val credentialsValidator: CredentialsValidator,
    private val cityFormatter: CityFormatter
) : BaseViewModel() {
    private val _signUpFlow = MutableStateFlow<ProfileUserUiModel?>(null)
    val signUpFlow: StateFlow<ProfileUserUiModel?>
        get() = _signUpFlow

    val errorsChannel = Channel<Throwable>()
}