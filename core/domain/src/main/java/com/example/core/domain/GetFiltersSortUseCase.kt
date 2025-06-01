package com.example.core.domain

import com.example.core.models.Filter

interface GetFiltersSortUseCase {
    operator fun invoke() : List<com.example.core.models.Filter>
}