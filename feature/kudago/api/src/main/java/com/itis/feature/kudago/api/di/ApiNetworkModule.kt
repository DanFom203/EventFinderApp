package com.itis.feature.kudago.api.di

import com.itis.common.core.config.NetworkProperties
import com.itis.common.di.scope.ApplicationScope
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.itis.feature.kudago.api.BuildConfig
import com.itis.feature.kudago.api.remote.KudagoApi
import okhttp3.logging.HttpLoggingInterceptor

@Module
class ApiNetworkModule {
    @Provides
    @ApplicationScope
    fun providePandasScoreApi(okHttpClient: OkHttpClient): KudagoApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.KUDAGO_API_EVENTS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(KudagoApi::class.java)
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(networkProperties: NetworkProperties): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(networkProperties.connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(networkProperties.writeTimeout, TimeUnit.SECONDS)
            .readTimeout(networkProperties.readTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return clientBuilder.build()
    }
}