package com.itis.feature.notes.impl.di

import android.content.Context
import com.itis.common.data.local.db.dao.NoteDao
import com.itis.common.di.CommonApi
import com.itis.common.di.scope.FeatureScope
import com.itis.feature.notes.api.di.NotesFeatureApi
import com.itis.feature.notes.impl.presentation.screens.add_note.di.AddNoteComponent
import com.itis.feature.notes.impl.presentation.screens.notes.di.NotesComponent
import com.itis.feature.notes.impl.utils.NotesFeatureRouter
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    modules = [NotesFeatureModule::class],
    dependencies = [NotesFeatureDependencies::class]
)
interface NotesFeatureComponent: NotesFeatureApi {

    fun notesComponentFactory(): NotesComponent.Factory

    fun addNoteComponentFactory(): AddNoteComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        @BindsInstance
        fun dao(noteDao: NoteDao): Builder
        @BindsInstance
        fun router(notesFeatureRouter: NotesFeatureRouter): Builder
        fun withDependencies(deps: NotesFeatureDependencies): Builder
        fun build(): NotesFeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class
        ]
    )
    interface NotesFeatureDependenciesComponent : NotesFeatureDependencies
}