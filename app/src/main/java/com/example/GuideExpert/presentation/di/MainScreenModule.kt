package com.example.GuideExpert.presentation.di

import com.example.GuideExpert.domain.GetConfigUseCase
import com.example.GuideExpert.domain.GetExcursionByFiltersUseCase
import com.example.GuideExpert.domain.GetExcursionByQueryUseCase
import com.example.GuideExpert.domain.GetExcursionDetailUseCase
import com.example.GuideExpert.domain.GetFiltersBarUseCase
import com.example.GuideExpert.domain.GetFiltersCategoriesUseCase
import com.example.GuideExpert.domain.GetFiltersDurationUseCase
import com.example.GuideExpert.domain.GetFiltersGroupsUseCase
import com.example.GuideExpert.domain.GetFiltersSortUseCase
import com.example.GuideExpert.domain.GetSortDefaultUseCase
import com.example.GuideExpert.domain.impl.GetConfigUseCaseImpl
import com.example.GuideExpert.domain.impl.GetExcursionByFiltersUseCaseImpl
import com.example.GuideExpert.domain.impl.GetExcursionByQueryUseCaseImpl
import com.example.GuideExpert.domain.impl.GetExcursionDetailUseCaseImpl
import com.example.GuideExpert.domain.impl.GetFiltersBarUseCaseImpl
import com.example.GuideExpert.domain.impl.GetFiltersCategoriesUseCaseImpl
import com.example.GuideExpert.domain.impl.GetFiltersDurationUseCaseImpl
import com.example.GuideExpert.domain.impl.GetFiltersGroupsUseCaseImpl
import com.example.GuideExpert.domain.impl.GetFiltersSortUseCaseImpl
import com.example.GuideExpert.domain.impl.GetSortDefaultUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainScreenModule {

    @Binds
    abstract fun bindGetExcursionDetailUseCase(
        getExcursionDetailUseCaseImpl: GetExcursionDetailUseCaseImpl
    ) : GetExcursionDetailUseCase

    @Binds
    abstract fun bindGetExcursionByQueryUseCase(
        getAllNotesUseCaseImpl: GetExcursionByQueryUseCaseImpl
    ) : GetExcursionByQueryUseCase

    @Binds
    abstract fun bindGetExcursionByFiltersUseCase(
        getAllNotesUseCaseImpl: GetExcursionByFiltersUseCaseImpl
    ) : GetExcursionByFiltersUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetFiltersBarUseCase(
        getFiltersBarUseCaseImpl: GetFiltersBarUseCaseImpl
    ) : GetFiltersBarUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetFiltersDurationUseCase(
        getFiltersDurationUseCaseImpl: GetFiltersDurationUseCaseImpl
    ) : GetFiltersDurationUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetFiltersSortUseCase(
        getFiltersSortUseCaseImpl: GetFiltersSortUseCaseImpl
    ) : GetFiltersSortUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetFiltersGroupsUseCase(
        getFiltersGroupsUseCaseImpl: GetFiltersGroupsUseCaseImpl
    ) : GetFiltersGroupsUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetFiltersCategoriesUseCase(
        getFiltersCategoriesUseCaseImpl: GetFiltersCategoriesUseCaseImpl
    ) : GetFiltersCategoriesUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetSortDefaultUseCase(
        getGetSortDefaultUseCaseImpl: GetSortDefaultUseCaseImpl
    ) : GetSortDefaultUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetConfigUseCase(
        getGetConfigUseCaseImpl: GetConfigUseCaseImpl
    ) : GetConfigUseCase

}