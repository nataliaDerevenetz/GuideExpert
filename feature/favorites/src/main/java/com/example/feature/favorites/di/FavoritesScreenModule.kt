package com.example.feature.favorites.di

import com.example.core.domain.GetExcursionFavoriteUseCase
import com.example.core.domain.RestoreFavoriteExcursionUseCase
import com.example.core.domain.impl.GetExcursionFavoriteUseCaseImpl
import com.example.core.domain.impl.RestoreFavoriteExcursionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoritesScreenModule {

    @ViewModelScoped
    @Binds
    abstract fun bindGetExcursionFavoriteUseCase(
        getExcursionFavoriteUseCaseImpl: GetExcursionFavoriteUseCaseImpl
    ) : GetExcursionFavoriteUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindRestoreFavoriteExcursionUseCase(
        restoreFavoriteExcursionUseCaseImpl: RestoreFavoriteExcursionUseCaseImpl
    ) : RestoreFavoriteExcursionUseCase

}