package com.example.GuideExpert.di

import com.example.GuideExpert.data.repository.DataSourceRepositoryImpl
import com.example.GuideExpert.data.storage.DBStorage
import com.example.GuideExpert.data.storage.DBStorageImpl
import com.example.GuideExpert.domain.repository.DataSourceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataSourceRepository(
       dbStorage: DBStorage
    ): DataSourceRepository {
        return DataSourceRepositoryImpl(dbStorage)
    }

    @Provides
    @Singleton
    fun provideDBStorage(
    ): DBStorage {
        return DBStorageImpl()
    }
}