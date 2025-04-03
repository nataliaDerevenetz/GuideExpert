package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.remote.pojo.ExcursionFavoritePOJO
import com.example.GuideExpert.data.remote.pojo.ExcursionsFavoriteResponsePOJO
import com.example.GuideExpert.data.remote.pojo.RemoveAvatarProfileResponsePOJO
import com.example.GuideExpert.data.remote.pojo.SetFavoriteExcursionResponsePOJO
import com.example.GuideExpert.data.remote.pojo.UpdateAvatarProfileResponsePOJO
import com.example.GuideExpert.data.remote.pojo.UpdateProfileResponsePOJO
import com.example.GuideExpert.domain.models.Avatar
import com.example.GuideExpert.domain.models.ExcursionFavorite
import com.example.GuideExpert.domain.models.ExcursionFavoriteResponse
import com.example.GuideExpert.domain.models.MessageResponse
import com.example.GuideExpert.domain.models.SetFavoriteExcursionResponse

fun UpdateAvatarProfileResponsePOJO.toAvatar(profileId:Int) = Avatar(id=idAvatar!!, profileId = profileId, url = imageUrl!!)

fun RemoveAvatarProfileResponsePOJO.toRemoveAvatarProfileResponse() = MessageResponse(success=success,message=message)

fun UpdateProfileResponsePOJO.toUpdateProfileResponse() = MessageResponse(success=success,message=message)

fun ExcursionsFavoriteResponsePOJO.toExcursionsFavoriteResponse() = ExcursionFavoriteResponse(success=success,message=message,
    excursions=excursions.map{it.toExcursionFavorite()})

fun ExcursionFavoritePOJO.toExcursionFavorite() = ExcursionFavorite(id = id,excursionId=excursionId,timestamp=timestamp)

//fun ExcursionsFavoriteResponsePOJO.toMessageResponse() =  MessageResponse(success=success,message=message)

fun SetFavoriteExcursionResponsePOJO.toSetFavoriteExcursionResponse() = SetFavoriteExcursionResponse(success=success,message=message,excursion=excursion.toExcursionFavorite())