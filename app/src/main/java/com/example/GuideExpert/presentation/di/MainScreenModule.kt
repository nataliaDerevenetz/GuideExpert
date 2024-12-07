package com.example.GuideExpert.presentation.di

import com.example.GuideExpert.domain.GetAllExcursionsUseCase
import com.example.GuideExpert.domain.GetExcursionDetailUseCase
import com.example.GuideExpert.domain.impl.GetAllExcursionsUseCaseImpl
import com.example.GuideExpert.domain.impl.GetExcursionDetailUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainScreenModule {

    @Binds
    abstract fun bindGetExcursionDetailUseCase(
        getExcursionDetailUseCaseImpl: GetExcursionDetailUseCaseImpl
    ) : GetExcursionDetailUseCase

    @Binds
    abstract fun bindGetExcursionAllUseCase(
        getAllNotesUseCaseImpl: GetAllExcursionsUseCaseImpl
    ) : GetAllExcursionsUseCase
}