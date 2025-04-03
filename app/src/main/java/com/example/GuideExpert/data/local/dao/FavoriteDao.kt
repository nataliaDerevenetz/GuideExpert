package com.example.GuideExpert.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.GuideExpert.data.local.models.ExcursionFavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteDao {
    @Query("SELECT * FROM excursionFavoriteEntity")
    fun getExcursionsFavorite() : Flow<List<ExcursionFavoriteEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFavoriteExcursion(excursions: List<ExcursionFavoriteEntity>)

    @Query("DELETE FROM excursionFavoriteEntity")
    suspend fun deleteFavoriteExcursion()

    @Transaction
    suspend fun insertAllExcursionsFavorite(excursions: List<ExcursionFavoriteEntity>) {
        deleteFavoriteExcursion()
        insertAllFavoriteExcursion(excursions)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExcursionFavorite(excursion: ExcursionFavoriteEntity)
}