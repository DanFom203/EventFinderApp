package com.itis.feature.notes.impl.di

import com.itis.common.di.scope.FeatureScope
import com.itis.feature.notes.api.domain.repository.NotesRepository
import com.itis.feature.notes.impl.data.repository.NotesRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class NotesFeatureModule {

    @Provides
    @FeatureScope
    fun provideNotesRepository(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository = notesRepositoryImpl
}