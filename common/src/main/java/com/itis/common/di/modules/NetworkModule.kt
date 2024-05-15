package com.itis.common.di.modules

import com.itis.common.core.config.AppProperties
import com.itis.common.core.config.NetworkProperties
import com.itis.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    @ApplicationScope
    fun provideNetworkProperties(appProperties: AppProperties): NetworkProperties {
        return appProperties.networkProperties()
    }

}