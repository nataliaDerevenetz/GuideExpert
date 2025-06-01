package com.example.core.domain

import com.example.core.models.Filter

interface GetFiltersDurationUseCase {
    operator fun invoke() : List<com.example.core.models.Filter>
}