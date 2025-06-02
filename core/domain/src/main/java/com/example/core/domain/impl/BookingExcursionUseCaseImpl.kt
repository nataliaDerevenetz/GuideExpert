package com.example.core.domain.impl

import com.example.core.domain.BookingExcursionUseCase
import com.example.core.models.MessageResponse
import com.example.core.models.UIResources
import com.example.core.domain.repository.ExcursionsRepository
import com.example.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookingExcursionUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val excursionsRepository: ExcursionsRepository
): BookingExcursionUseCase {
    override suspend operator fun invoke(count: String, email: String, phone: String, comments:String, date: String,time:String, excursionId:Int): Flow<UIResources<MessageResponse>> {
        val profile = profileRepository.profileFlow.value
        return excursionsRepository.bookingExcursion(count,email,phone,comments,date,time,excursionId,profile)
    }
}