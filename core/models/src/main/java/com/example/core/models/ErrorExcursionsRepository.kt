package com.example.core.models

sealed class ErrorExcursionsRepository {
    data object Authorization : ErrorExcursionsRepository()
    data object LoadingFavorites : ErrorExcursionsRepository()
}