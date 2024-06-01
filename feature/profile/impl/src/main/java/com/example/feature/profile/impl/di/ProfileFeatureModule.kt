package com.example.feature.profile.impl.di

import com.example.feature.profile.api.domain.repository.ProfileRepository
import com.example.feature.profile.impl.data.repository.ProfileRepositoryImpl
import com.itis.common.di.scope.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class ProfileFeatureModule {

    @Provides
    @FeatureScope
    fun provideProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository = profileRepositoryImpl
}