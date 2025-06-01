package com.example.feature.profile.di

import com.example.core.domain.DeleteAvatarProfileUseCase
import com.example.core.domain.GetAuthTokenByYandexUseCase
import com.example.core.domain.LogoutProfileUseCase
import com.example.core.domain.UpdateAvatarProfileUseCase
import com.example.core.domain.UpdateProfileUseCase
import com.example.core.domain.impl.DeleteAvatarProfileUseCaseImpl
import com.example.core.domain.impl.GetAuthTokenByYandexUseCaseImpl
import com.example.core.domain.impl.LogoutProfileUseCaseImpl
import com.example.core.domain.impl.UpdateAvatarProfileUseCaseImpl
import com.example.core.domain.impl.UpdateProfileUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class ProfileScreenModule {

    @ViewModelScoped
    @Binds
    abstract fun bindGetAuthTokenByYandexUseCase(
        getAuthTokenByYandexUseCaseImpl: GetAuthTokenByYandexUseCaseImpl
    ) : GetAuthTokenByYandexUseCase


    @ViewModelScoped
    @Binds
    abstract fun bindUpdateAvatarProfileUseCase(
        updateAvatarProfileUseCaseImpl: UpdateAvatarProfileUseCaseImpl
    ) : UpdateAvatarProfileUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindLogoutProfileUseCase(
        updateLogoutProfileUseCaseImpl: LogoutProfileUseCaseImpl
    ) : LogoutProfileUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindDeleteAvatarProfileUseCase(
        deleteAvatarProfileUseCaseImpl: DeleteAvatarProfileUseCaseImpl
    ) : DeleteAvatarProfileUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindUpdateProfileUseCase(
        updateProfileUseCaseImpl: UpdateProfileUseCaseImpl
    ) : UpdateProfileUseCase

}