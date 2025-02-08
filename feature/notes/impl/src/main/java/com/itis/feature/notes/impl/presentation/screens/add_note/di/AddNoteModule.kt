package com.itis.feature.notes.impl.presentation.screens.add_note.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.common.data.storage.PreferencesImpl
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.feature.notes.impl.domain.usecase.SaveUsersNoteUseCase
import com.itis.feature.notes.impl.presentation.screens.add_note.AddNoteViewModel
import com.itis.feature.notes.api.utils.NotesFeatureRouter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class AddNoteModule {
    @Provides
    fun provideMainViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): AddNoteViewModel {
        return ViewModelProvider(fragment, factory)[AddNoteViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(AddNoteViewModel::class)]
    fun provideAddNoteViewModel (
        saveUsersNoteUseCase: SaveUsersNoteUseCase,
        router: NotesFeatureRouter,
        exceptionHandlerDelegate: ExceptionHandlerDelegate,
        preferencesImpl: PreferencesImpl
    ): ViewModel {
        return AddNoteViewModel(saveUsersNoteUseCase,router, exceptionHandlerDelegate, preferencesImpl)
    }
}