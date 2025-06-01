package com.example.GuideExpert.presentation.di

import com.example.core.domain.GetExcursionsFavoriteIdUseCase
import com.example.core.domain.GetProfileUseCase
import com.example.core.domain.impl.GetExcursionsFavoriteIdUseCaseImpl
import com.example.core.domain.impl.GetProfileUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainScreenModule {

    @ViewModelScoped
    @Binds
    abstract fun bindGetProfileUseCase(
        getProfileUseCaseImpl: GetProfileUseCaseImpl
    ) : GetProfileUseCase


    @ViewModelScoped
    @Binds
    abstract fun bindGetExcursionsFavoriteIdUseCase(
        getExcursionsFavoriteIdUseCaseImpl: GetExcursionsFavoriteIdUseCaseImpl
    ) : GetExcursionsFavoriteIdUseCase

}