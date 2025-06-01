package com.example.core.domain

import com.example.core.models.Filter

interface GetFiltersGroupsUseCase {
    operator fun invoke() : List<com.example.core.models.Filter>
}