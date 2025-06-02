package com.example.core.data.models

import com.example.core.database.models.ExcursionFavoriteEntity
import com.example.core.models.DeleteFavoriteExcursionResponse
import com.example.core.models.ExcursionFavorite
import com.example.core.models.ExcursionFavoriteResponse
import com.example.core.models.RestoreFavoriteExcursionResponse
import com.example.core.models.SetFavoriteExcursionResponse
import com.example.core.network.pojo.DeleteFavoriteExcursionResponsePOJO
import com.example.core.network.pojo.ExcursionFavoritePOJO
import com.example.core.network.pojo.ExcursionsFavoriteResponsePOJO
import com.example.core.network.pojo.RestoreFavoriteExcursionResponsePOJO
import com.example.core.network.pojo.SetFavoriteExcursionResponsePOJO

fun ExcursionFavoriteEntity.toExcursionFavorite() =
    ExcursionFavorite(
        id = id,
        excursionId = excursionId,
        timestamp = timestamp
    )

fun ExcursionFavorite.toExcursionsFavoriteEntity() =
    ExcursionFavoriteEntity(
        id = id,
        excursionId = excursionId,
        timestamp
    )

fun ExcursionFavoritePOJO.toExcursionFavorite() = ExcursionFavorite(
    id = id,
    excursionId = excursionId,
    timestamp = timestamp
)

fun ExcursionsFavoriteResponsePOJO.toExcursionsFavoriteResponse() =
   ExcursionFavoriteResponse(
        success = success, message = message,
        excursions = excursions.map { it.toExcursionFavorite() })

fun SetFavoriteExcursionResponsePOJO.toSetFavoriteExcursionResponse() =
    SetFavoriteExcursionResponse(
        success = success,
        message = message,
        excursion = excursion.toExcursionFavorite()
    )

fun DeleteFavoriteExcursionResponsePOJO.toDeleteFavoriteExcursionResponse() =
    DeleteFavoriteExcursionResponse(
        success = success,
        message = message,
        excursion = excursion.toExcursionFavorite()
    )

fun RestoreFavoriteExcursionResponsePOJO.toRestoreFavoriteExcursionResponse() =
    RestoreFavoriteExcursionResponse(
        success = success,
        message = message,
        excursion = excursion.toExcursionFavorite()
    )

