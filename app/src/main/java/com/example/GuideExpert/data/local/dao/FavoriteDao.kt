package com.example.GuideExpert.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.GuideExpert.data.local.models.ExcursionFavoriteEntity
import com.example.GuideExpert.data.local.models.ExcursionsFavoriteEntity
import com.example.GuideExpert.data.local.models.ExcursionsFavoriteWithData
import com.example.GuideExpert.data.local.models.ImagePreviewFavoriteEntity
import com.example.GuideExpert.data.mappers.toExcursionsFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM excursionFavoriteEntity")
    fun getAll() : Flow<List<ExcursionFavoriteEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExcursion(excursions: List<ExcursionFavoriteEntity>)

    @Query("DELETE FROM excursionFavoriteEntity")
    suspend fun deleteAll()

    @Transaction
    suspend fun insertAll(excursions: List<ExcursionFavoriteEntity>) {
        deleteAll()
        insertAllExcursion(excursions)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(excursion: ExcursionFavoriteEntity)

    @Delete
    suspend fun delete(excursion: ExcursionFavoriteEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImagePreviewFavoriteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExcursion(excursion: List<ExcursionsFavoriteEntity>)

    @Query("DELETE FROM excursionsFavoriteEntity")
    suspend fun clearAllExcursion()

    @Transaction
    suspend fun insertFavorites(excursions: List<ExcursionsFavoriteWithData>) {
        clearAllExcursion()
        insertExcursion(excursions.map{ it.toExcursionsFavoriteEntity() })
        excursions.forEach { insertImages(it.images) }
    }
}