package com.itis.feature.notes.impl.presentation.screens.notes.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.common.data.storage.PreferencesImpl
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.feature.notes.impl.domain.usecase.DeleteUsersNoteUseCase
import com.itis.feature.notes.impl.domain.usecase.GetUsersNotesUseCase
import com.itis.feature.notes.impl.presentation.screens.notes.NotesViewModel
import com.itis.feature.notes.api.utils.NotesFeatureRouter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class NotesModule {
    @Provides
    fun provideMainViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): NotesViewModel {
        return ViewModelProvider(fragment, factory)[NotesViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(NotesViewModel::class)]
    fun provideNotesViewModel (
        getUsersNotesUseCase: GetUsersNotesUseCase,
        deleteUsersNoteUseCase: DeleteUsersNoteUseCase,
        router: NotesFeatureRouter,
        exceptionHandlerDelegate: ExceptionHandlerDelegate,
        preferencesImpl: PreferencesImpl
    ): ViewModel {
        return NotesViewModel(getUsersNotesUseCase,deleteUsersNoteUseCase,router, exceptionHandlerDelegate, preferencesImpl)
    }
}