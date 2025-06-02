package com.example.core.data.di

import com.example.core.data.local.DBStorage
import com.example.core.data.local.DBStorageImpl
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