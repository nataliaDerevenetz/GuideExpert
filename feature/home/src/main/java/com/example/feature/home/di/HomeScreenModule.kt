package com.example.feature.home.di

import com.example.core.domain.BookingExcursionUseCase
import com.example.core.domain.DeleteFavoriteExcursionUseCase
import com.example.core.domain.GetConfigUseCase
import com.example.core.domain.GetExcursionByFiltersUseCase
import com.example.core.domain.GetExcursionByQueryUseCase
import com.example.core.domain.GetExcursionDetailUseCase
import com.example.core.domain.SetFavoriteExcursionUseCase
import com.example.core.domain.impl.BookingExcursionUseCaseImpl
import com.example.core.domain.impl.DeleteFavoriteExcursionUseCaseImpl
import com.example.core.domain.impl.GetConfigUseCaseImpl
import com.example.core.domain.impl.GetExcursionByFiltersUseCaseImpl
import com.example.core.domain.impl.GetExcursionByQueryUseCaseImpl
import com.example.core.domain.impl.GetExcursionDetailUseCaseImpl
import com.example.core.domain.impl.SetFavoriteExcursionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeScreenModule {

    @Binds
    abstract fun bindGetExcursionDetailUseCase(
        getExcursionDetailUseCaseImpl: GetExcursionDetailUseCaseImpl
    ) : GetExcursionDetailUseCase

    @Binds
    abstract fun bindGetExcursionByQueryUseCase(
        getExcursionByQueryUseCaseImpl: GetExcursionByQueryUseCaseImpl
    ) : GetExcursionByQueryUseCase

    @Binds
    abstract fun bindGetExcursionByFiltersUseCase(
        getExcursionByFiltersUseCaseImpl: GetExcursionByFiltersUseCaseImpl
    ) : GetExcursionByFiltersUseCase
    
    @ViewModelScoped
    @Binds
    abstract fun bindGetConfigUseCase(
        getGetConfigUseCaseImpl: GetConfigUseCaseImpl
    ) : GetConfigUseCase


    @ViewModelScoped
    @Binds
    abstract fun bindSetFavoriteExcursionUseCase(
        setFavoriteExcursionUseCaseImpl: SetFavoriteExcursionUseCaseImpl
    ) : SetFavoriteExcursionUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindDeleteFavoriteExcursionUseCase(
        deleteFavoriteExcursionUseCaseImpl: DeleteFavoriteExcursionUseCaseImpl
    ) : DeleteFavoriteExcursionUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindBookingExcursionUseCase(
        bookingExcursionUseCaseImpl: BookingExcursionUseCaseImpl
    ) : BookingExcursionUseCase
}