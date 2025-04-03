package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.remote.pojo.ExcursionFavoritePOJO
import com.example.GuideExpert.data.remote.pojo.ExcursionsFavoriteResponsePOJO
import com.example.GuideExpert.data.remote.pojo.RemoveAvatarProfileResponsePOJO
import com.example.GuideExpert.data.remote.pojo.UpdateAvatarProfileResponsePOJO
import com.example.GuideExpert.data.remote.pojo.UpdateProfileResponsePOJO
import com.example.GuideExpert.domain.models.Avatar
import com.example.GuideExpert.domain.models.ExcursionFavorite
import com.example.GuideExpert.domain.models.ExcursionsFavoriteResponse
import com.example.GuideExpert.domain.models.RemoveAvatarProfileResponse
import com.example.GuideExpert.domain.models.UpdateProfileResponse

fun UpdateAvatarProfileResponsePOJO.toAvatar(profileId:Int) = Avatar(id=idAvatar!!, profileId = profileId, url = imageUrl!!)

fun RemoveAvatarProfileResponsePOJO.toRemoveAvatarProfileResponse() = RemoveAvatarProfileResponse(success=success,message=message)

fun UpdateProfileResponsePOJO.toUpdateProfileResponse() = UpdateProfileResponse(success=success,message=message)

fun ExcursionsFavoriteResponsePOJO.toExcursionsFavoriteResponse() = ExcursionsFavoriteResponse(success=success,message=message,
    excursions=excursions.map{it.toExcursionFavorite()})

fun ExcursionFavoritePOJO.toExcursionFavorite() = ExcursionFavorite(id = id,excursionId=excursionId,timestamp=timestamp)
