package com.example.GuideExpert.di

import com.example.GuideExpert.domain.GetExcursionAllUseCase
import com.example.GuideExpert.domain.GetExcursionDetailUseCase
import com.example.GuideExpert.domain.impl.GetExcursionAllUseCaseImpl
import com.example.GuideExpert.domain.impl.GetExcursionDetailUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MainScreenModule {

    @Binds
    abstract fun bindGetExcursionDetailUseCase(
        getExcursionDetailUseCaseImpl: GetExcursionDetailUseCaseImpl
    ) : GetExcursionDetailUseCase

    @Binds
    abstract fun bindGetExcursionAllUseCase(
        getAllNotesUseCaseImpl: GetExcursionAllUseCaseImpl
    ) : GetExcursionAllUseCase
}