package com.itis.eventfinderapp.di.main.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.common.storage.PreferencesImpl
import com.itis.eventfinderapp.di.main.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class MainModule {

    @Provides
    fun provideViewModelCreator(
        activity: AppCompatActivity,
        viewModelFactory: ViewModelProvider.Factory
    ): MainViewModel {
        return ViewModelProvider(activity, viewModelFactory)[MainViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(MainViewModel::class)]
    fun provideViewModel(preferencesImpl: PreferencesImpl): ViewModel {
        return MainViewModel(preferencesImpl)
    }
}