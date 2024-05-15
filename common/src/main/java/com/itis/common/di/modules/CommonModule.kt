package com.itis.common.di.modules

import android.app.NotificationManager
import android.content.Context
import com.itis.common.core.config.AppProperties
import com.itis.common.core.resources.ResourceManager
import com.itis.common.core.resources.ResourceManagerImpl
import com.itis.common.di.scope.ApplicationScope
import com.itis.common.notification.NotificationManagerWrapper
import com.itis.common.notification.NotificationManagerWrapperImpl
import com.itis.common.utils.DateFormatter
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class CommonModule {

    @Provides
    @ApplicationScope
    fun provideResourceManager(context: Context): ResourceManager {
        return ResourceManagerImpl(context)
    }

    @Provides
    @ApplicationScope
    fun provideAppProperties(context: Context): AppProperties {
        return AppProperties(context)
    }

    @Provides
    fun provideDateFormatter(): DateFormatter {
        return DateFormatter()
    }

    @Provides
    fun provideNotificationWrapper(
        context: Context,
        notificationManager: NotificationManager
    ): NotificationManagerWrapper {
        return NotificationManagerWrapperImpl(context, notificationManager)
    }

    @Provides
    fun provideCorutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

}