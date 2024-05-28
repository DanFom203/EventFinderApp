package com.itis.common.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {
    @ApplicationScope
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @ApplicationScope
    @Provides
    fun provideStorage(): FirebaseFirestore = FirebaseFirestore.getInstance()
}