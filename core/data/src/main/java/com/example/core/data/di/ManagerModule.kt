package com.example.core.data.di

import android.content.Context
import com.example.core.data.repository.SessionManagerImpl
import com.example.core.domain.repository.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {
    @Provides
    @Singleton
    fun providesSessionManager(@ApplicationContext appContext: Context): SessionManager {
        return SessionManagerImpl(appContext)
    }
}