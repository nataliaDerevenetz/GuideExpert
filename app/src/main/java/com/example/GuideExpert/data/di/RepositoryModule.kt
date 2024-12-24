package com.example.GuideExpert.data.di

import android.content.Context
import androidx.room.Room
import com.example.GuideExpert.data.repository.DataSourceRepositoryImpl
import com.example.GuideExpert.data.local.DBStorage
import com.example.GuideExpert.data.local.DBStorageImpl
import com.example.GuideExpert.data.local.db.ExcursionsRoomDatabase
import com.example.GuideExpert.data.repository.DataPagingRepositoryImpl
import com.example.GuideExpert.domain.repository.DataPagingRepository
import com.example.GuideExpert.domain.repository.DataSourceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleBinder {

    @Singleton
    @Binds
    abstract fun bindDataPagingRepository(
        dataPagingRepositoryImpl: DataPagingRepositoryImpl
    ) : DataPagingRepository

    @Singleton
    @Binds
    abstract fun bindDataSourceRepository(
        dataSourceRepositoryImpl: DataSourceRepositoryImpl
    ) : DataSourceRepository

}