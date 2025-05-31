package com.example.GuideExpert.data.models

import com.example.GuideExpert.data.remote.pojo.BookingExcursionPOJO
import com.example.GuideExpert.data.remote.pojo.RemoveAvatarProfileResponsePOJO
import com.example.GuideExpert.data.remote.pojo.UpdateProfileResponsePOJO
import com.example.GuideExpert.domain.models.MessageResponse

fun RemoveAvatarProfileResponsePOJO.toRemoveAvatarProfileResponse() = MessageResponse(success=success,message=message)

fun UpdateProfileResponsePOJO.toUpdateProfileResponse() = MessageResponse(success=success,message=message)

fun BookingExcursionPOJO.toMessageResponse() = MessageResponse(success=success,message=message)
