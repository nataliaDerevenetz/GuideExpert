package com.example.core.domain

import com.example.core.models.Filter

interface GetFiltersBarUseCase {
    operator fun invoke() : List<com.example.core.models.Filter>
}