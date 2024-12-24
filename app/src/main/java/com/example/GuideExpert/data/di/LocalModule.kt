package com.example.GuideExpert.data.di

import android.content.Context
import androidx.room.Room
import com.example.GuideExpert.data.local.db.ExcursionsRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalSourceModuleProvider {

    @Provides
    fun provideExcursionDao(database: ExcursionsRoomDatabase) = database.excursionDao()

    @Provides
    fun provideRemoteKeyDao(database: ExcursionsRoomDatabase) = database.remoteKeyDao()

    @Provides
    @Singleton
    fun providesLocalDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ExcursionsRoomDatabase::class.java,
        "excursions-database"
    ).build()
}