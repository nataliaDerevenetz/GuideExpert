package com.example.GuideExpert.data.di

import com.example.GuideExpert.data.local.DBStorage
import com.example.GuideExpert.data.local.DBStorageImpl
import com.example.GuideExpert.data.repository.DataPagingRepositoryImpl
import com.example.GuideExpert.data.repository.DataSourceRepositoryImpl
import com.example.GuideExpert.domain.repository.DataPagingRepository
import com.example.GuideExpert.domain.repository.DataSourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModuleBinder {
    @Singleton
    @Binds
    abstract fun bindDBStorageImpl(
        roomLocalDataSource: DBStorageImpl
    ) : DBStorage
}