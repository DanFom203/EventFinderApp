package com.itis.common.di.modules

import android.content.Context
import com.itis.common.data.local.db.AppDatabase
import com.itis.common.data.local.db.dao.NoteDao
import com.itis.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class RoomDatabaseModule {

    @Provides
    @ApplicationScope
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.get(context)
    }

    @ApplicationScope
    @Provides
    fun provideNoteDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.noteDao
    }
}