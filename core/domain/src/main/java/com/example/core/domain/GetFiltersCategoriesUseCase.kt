package com.example.core.domain

import com.example.core.models.Filter

interface GetFiltersCategoriesUseCase {
    operator fun invoke() : List<com.example.core.models.Filter>
}