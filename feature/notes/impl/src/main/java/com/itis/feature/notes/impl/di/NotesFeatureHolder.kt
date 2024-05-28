package com.itis.feature.notes.impl.di

import android.content.Context
import com.itis.common.data.local.db.dao.NoteDao
import com.itis.common.di.FeatureApiHolder
import com.itis.common.di.FeatureContainer
import com.itis.common.di.scope.ApplicationScope
import com.itis.feature.notes.impl.utils.NotesFeatureRouter
import javax.inject.Inject

@ApplicationScope
class NotesFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val router: NotesFeatureRouter,
    private val dao: NoteDao,
    private val context: Context
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        val notesFeatureDependencies =
            DaggerNotesFeatureComponent_NotesFeatureDependenciesComponent.builder()
                .commonApi(commonApi())
                .build()
        return DaggerNotesFeatureComponent.builder()
            .withDependencies(notesFeatureDependencies)
            .context(context)
            .dao(dao)
            .router(router)
            .build()
    }
}