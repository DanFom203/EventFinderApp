package com.itis.feature.notes.impl.presentation.screens.add_note.di

import androidx.fragment.app.Fragment
import com.itis.feature.notes.impl.presentation.screens.add_note.AddNoteFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [AddNoteModule::class]
)
interface AddNoteComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance fragment: Fragment
        ): AddNoteComponent
    }
    fun inject(fragment: AddNoteFragment)
}