package com.itis.feature.auth.impl.di

import com.itis.common.di.scope.FeatureScope
import com.itis.feature.auth.api.domain.repository.UserRepository
import com.itis.feature.auth.impl.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class AuthFeatureModule {
    @Provides
    @FeatureScope
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository = userRepositoryImpl

}