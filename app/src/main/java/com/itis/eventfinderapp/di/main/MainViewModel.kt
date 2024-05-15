package com.itis.eventfinderapp.di.main

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.itis.common.base.BaseViewModel
import com.itis.common.storage.PreferencesImpl
import com.itis.common.utils.AsyncResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val preferencesImpl: PreferencesImpl
) : BaseViewModel() {
    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            updateBottomNavigationView()
        }
    init {
        preferencesImpl.prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }
    private val _sharedPreferencesFlow = MutableStateFlow<AsyncResult<Boolean>?>(null)
    val sharedPreferencesFlow: MutableStateFlow<AsyncResult<Boolean>?>
        get() = _sharedPreferencesFlow

    fun updateBottomNavigationView() {
        viewModelScope.launch {
            try {
                val authStatus = preferencesImpl.getAutStatus()
                _sharedPreferencesFlow.emit(AsyncResult.Success(authStatus))
            } catch (e: Exception) {
                _sharedPreferencesFlow.emit(AsyncResult.Error(e))
            }
        }

    }
    fun getAuthValue():Boolean{
        return preferencesImpl.getAutStatus()
    }
}