package com.example.GuideExpert.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.GuideExpert.data.local.models.ExcursionsFavoriteEntity
import com.example.GuideExpert.data.local.models.ExcursionsFavoriteWithData
import com.example.GuideExpert.data.local.models.ImagePreviewFavoriteEntity
import com.example.GuideExpert.data.mappers.toExcursionsFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExcursionsFavoriteDao {
    @Query("SELECT * FROM excursionsFavoriteEntity")
    fun getAll() : Flow<List<ExcursionsFavoriteWithData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImagePreviewFavoriteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExcursion(excursion: List<ExcursionsFavoriteEntity>)

    @Query("DELETE FROM excursionsFavoriteEntity")
    suspend fun clearAll()

    @Transaction
    suspend fun insertAll(excursions: List<ExcursionsFavoriteWithData>) {
        clearAll()
        insertExcursion(excursions.map{ it.toExcursionsFavoriteEntity() })
        excursions.forEach { insertImages(it.images) }
    }
}