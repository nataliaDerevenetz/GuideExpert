package com.example.GuideExpert.domain

//import com.example.GuideExpert.ExcursionData
import com.example.GuideExpert.domain.models.ExcursionData
import kotlinx.coroutines.flow.Flow

interface GetExcursionDetailUseCase {
     operator fun invoke(excursionId: Int) : Flow<ExcursionData>
}