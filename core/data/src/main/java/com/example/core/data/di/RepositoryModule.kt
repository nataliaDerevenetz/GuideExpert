package com.example.core.data.di

import com.example.core.data.repository.DataProviderRepositoryImpl
import com.example.core.data.repository.DataSourceRepositoryImpl
import com.example.core.data.repository.ExcursionsRepositoryImpl
import com.example.core.data.repository.ProfileRepositoryImpl
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
    ) : DataSourceRepository

    @Singleton
    @Binds
    abstract fun bindExcursionsRepository(
        excursionsRepositoryImpl: ExcursionsRepositoryImpl
    ) : ExcursionsRepository

    @Singleton
    @Binds
    abstract fun bindDataProviderRepository(
        dataProviderRepositoryImpl: DataProviderRepositoryImpl
    ) : DataProviderRepository

    @Singleton
    @Binds
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ) : ProfileRepository

}