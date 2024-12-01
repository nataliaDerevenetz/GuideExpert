package com.example.GuideExpert.domain

import com.example.GuideExpert.ExcursionData
import com.example.GuideExpert.data.Excursion
import kotlinx.coroutines.flow.Flow

interface GetExcursionDetailUseCase {
     operator fun invoke(excursionId: Int) : Flow<ExcursionData>
}