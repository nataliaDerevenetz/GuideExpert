package com.example.GuideExpert.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.GuideExpert.data.local.models.ImagePreviewSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagePreviewSearchDao {

    @Query("SELECT * FROM imagePreviewSearchEntity WHERE excursionId = :id")
    fun getByExcursionId(id: Int) : Flow<List<ImagePreviewSearchEntity>>

    @Query("SELECT * FROM imagePreviewSearchEntity WHERE id = :id")
    fun getById(id: Int) : Flow<ImagePreviewSearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<ImagePreviewSearchEntity>)

}