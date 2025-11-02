package com.example.core.data.models

import com.example.core.models.MessageResponse
import com.example.core.models.SetTokenDeviceResponse
import com.example.core.network.pojo.BookingExcursionPOJO
import com.example.core.network.pojo.RegisterTokenDeviceResponsePOJO
import com.example.core.network.pojo.RemoveAvatarProfileResponsePOJO
import com.example.core.network.pojo.UpdateProfileResponsePOJO

fun RemoveAvatarProfileResponsePOJO.toRemoveAvatarProfileResponse() =
    MessageResponse(success = success, message = message)

fun UpdateProfileResponsePOJO.toUpdateProfileResponse() =
    MessageResponse(success = success, message = message)

fun BookingExcursionPOJO.toMessageResponse() =
    MessageResponse(success = success, message = message)

fun RegisterTokenDeviceResponsePOJO.toSetTokenDeviceResponse() =
    SetTokenDeviceResponse(success = success, message = message,timestamp = timestamp)
