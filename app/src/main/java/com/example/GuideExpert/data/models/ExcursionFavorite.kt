package com.example.GuideExpert.data.models

import com.example.GuideExpert.data.local.models.ExcursionFavoriteEntity
import com.example.GuideExpert.data.remote.pojo.DeleteFavoriteExcursionResponsePOJO
import com.example.GuideExpert.data.remote.pojo.ExcursionFavoritePOJO
import com.example.GuideExpert.data.remote.pojo.ExcursionsFavoriteResponsePOJO
import com.example.GuideExpert.data.remote.pojo.RestoreFavoriteExcursionResponsePOJO
import com.example.GuideExpert.data.remote.pojo.SetFavoriteExcursionResponsePOJO
import com.example.GuideExpert.domain.models.DeleteFavoriteExcursionResponse
import com.example.GuideExpert.domain.models.ExcursionFavorite
import com.example.GuideExpert.domain.models.ExcursionFavoriteResponse
import com.example.GuideExpert.domain.models.RestoreFavoriteExcursionResponse
import com.example.GuideExpert.domain.models.SetFavoriteExcursionResponse

fun ExcursionFavoriteEntity.toExcursionFavorite() = ExcursionFavorite(id=id,excursionId=excursionId,timestamp=timestamp)

fun ExcursionFavorite.toExcursionsFavoriteEntity() =  ExcursionFavoriteEntity(id=id,excursionId=excursionId,timestamp)

fun ExcursionFavoritePOJO.toExcursionFavorite() = ExcursionFavorite(id = id,excursionId=excursionId,timestamp=timestamp)

fun ExcursionsFavoriteResponsePOJO.toExcursionsFavoriteResponse() = ExcursionFavoriteResponse(success=success,message=message,
    excursions=excursions.map{it.toExcursionFavorite()})

fun SetFavoriteExcursionResponsePOJO.toSetFavoriteExcursionResponse() = SetFavoriteExcursionResponse(success=success,message=message,excursion=excursion.toExcursionFavorite())

fun DeleteFavoriteExcursionResponsePOJO.toDeleteFavoriteExcursionResponse() = DeleteFavoriteExcursionResponse(success=success,message=message,excursion=excursion.toExcursionFavorite())

fun RestoreFavoriteExcursionResponsePOJO.toRestoreFavoriteExcursionResponse() = RestoreFavoriteExcursionResponse(success=success,message=message,excursion=excursion.toExcursionFavorite())

