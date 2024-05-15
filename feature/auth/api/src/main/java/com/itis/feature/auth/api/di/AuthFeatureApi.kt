package com.itis.feature.auth.api.di

import android.content.Context
import com.itis.common.core.preferences.Preferences
import com.itis.feature.auth.api.domain.repository.UserRepository

interface AuthFeatureApi {
    fun provideUserRepository(): UserRepository
    fun providePreferences(): Preferences
    fun applicationContext(): Context
}