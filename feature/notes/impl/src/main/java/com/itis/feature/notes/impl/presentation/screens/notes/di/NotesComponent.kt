package com.itis.feature.notes.impl.presentation.screens.notes.di

import androidx.fragment.app.Fragment
import com.itis.feature.notes.impl.presentation.screens.notes.NotesFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [NotesModule::class]
)
interface NotesComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance fragment: Fragment
        ): NotesComponent
    }
    fun inject(fragment: NotesFragment)
}