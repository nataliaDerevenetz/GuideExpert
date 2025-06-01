package com.example.GuideExpert.data.di

import com.example.GuideExpert.data.repository.DataProviderRepositoryImpl
import com.example.GuideExpert.data.repository.DataSourceRepositoryImpl
import com.example.GuideExpert.data.repository.ExcursionsRepositoryImpl
import com.example.GuideExpert.data.repository.ProfileRepositoryImpl
import com.example.core.domain.repository.DataProviderRepository
import com.example.core.domain.repository.DataSourceRepository
import com.example.core.domain.repository.ExcursionsRepository
import com.example.core.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleBinder {

    @Singleton
    @Binds
    abstract fun bindDataSourceRepository(
        dataSourceRepositoryImpl: DataSourceRepositoryImpl
    ) : com.example.core.domain.repository.DataSourceRepository

    @Singleton
    @Binds
    abstract fun bindExcursionsRepository(
        excursionsRepositoryImpl: ExcursionsRepositoryImpl
    ) : com.example.core.domain.repository.ExcursionsRepository

    @Singleton
    @Binds
    abstract fun bindDataProviderRepository(
        dataProviderRepositoryImpl: DataProviderRepositoryImpl
    ) : com.example.core.domain.repository.DataProviderRepository

    @Singleton
    @Binds
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ) : com.example.core.domain.repository.ProfileRepository

}