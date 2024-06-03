package com.example.feature.profile.impl.presentation.screens.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.feature.profile.impl.domain.usecase.GetUserProfileInfoUseCase
import com.example.feature.profile.impl.domain.usecase.LogoutUserUseCase
import com.example.feature.profile.impl.domain.usecase.UpdateUserProfilePictureUseCase
import com.example.feature.profile.impl.presentation.model.ProfileUserUiModel
import com.example.feature.profile.impl.utils.ProfileFeatureRouter
import com.itis.common.base.BaseViewModel
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.common.utils.runCatching
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserProfileInfoUseCase: GetUserProfileInfoUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val updateUserProfilePictureUseCase: UpdateUserProfilePictureUseCase,
    private val router: ProfileFeatureRouter,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate
) : BaseViewModel() {
    private val _currentUserProfileInfoFlow = MutableStateFlow<ProfileUserUiModel?>(null)
    val currentUserProfileInfoFlow: StateFlow<ProfileUserUiModel?>
        get() = _currentUserProfileInfoFlow

    val errorsChannel = Channel<Throwable>()

    fun initialize() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                getUserProfileInfoUseCase.invoke()
            }.onSuccess {
                _currentUserProfileInfoFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun openChangeCredentialsScreen() {
        router.openChangeCredentialsScreenFromProfileScreen()
    }

    fun logout() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                logoutUserUseCase.invoke()
            }.onSuccess {
                openInitialScreen()
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun updateProfilePicture(imageUri: Uri) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                updateUserProfilePictureUseCase.invoke(imageUri)
            }.onSuccess {
                _currentUserProfileInfoFlow.value = _currentUserProfileInfoFlow.value?.copy(avatar = it)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun openFavouriteEventsScreen() {
        router.openFavouriteEventsFromProfileScreen()
    }

    private fun openInitialScreen() {
        router.openInitialScreenFromProfileScreen()
    }
}