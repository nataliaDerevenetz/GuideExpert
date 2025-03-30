package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.remote.pojo.ExcursionsFavoriteIdResponsePOJO
import com.example.GuideExpert.data.remote.pojo.RemoveAvatarProfileResponsePOJO
import com.example.GuideExpert.data.remote.pojo.UpdateAvatarProfileResponsePOJO
import com.example.GuideExpert.data.remote.pojo.UpdateProfileResponsePOJO
import com.example.GuideExpert.domain.models.Avatar
import com.example.GuideExpert.domain.models.ExcursionsFavoriteIdResponse
import com.example.GuideExpert.domain.models.RemoveAvatarProfileResponse
import com.example.GuideExpert.domain.models.UpdateProfileResponse

fun UpdateAvatarProfileResponsePOJO.toAvatar(profileId:Int) = Avatar(id=idAvatar!!, profileId = profileId, url = imageUrl!!)

fun RemoveAvatarProfileResponsePOJO.toRemoveAvatarProfileResponse() = RemoveAvatarProfileResponse(success=success,message=message)

fun UpdateProfileResponsePOJO.toUpdateProfileResponse() = UpdateProfileResponse(success=success,message=message)

fun ExcursionsFavoriteIdResponsePOJO.toExcursionsFavoriteIdResponse() = ExcursionsFavoriteIdResponse(success=success,message=message,excursions=excursions)
