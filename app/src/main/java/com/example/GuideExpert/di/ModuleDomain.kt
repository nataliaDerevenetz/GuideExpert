package com.example.GuideExpert.di

import com.example.GuideExpert.UserInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideUserInfoRepository(
        // Potential dependencies of this type
    ): UserInfoRepository {
        return UserInfoRepository()
    }
}