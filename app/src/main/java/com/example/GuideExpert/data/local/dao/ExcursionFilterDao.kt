package com.example.GuideExpert.data.local.dao

import android.util.Log
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.GuideExpert.data.local.models.ExcursionFilterEntity
import com.example.GuideExpert.data.local.models.ExcursionFilterWithData
import com.example.GuideExpert.data.local.models.ExcursionSearchEntity
import com.example.GuideExpert.data.local.models.ExcursionSearchWithData
import com.example.GuideExpert.data.local.models.ImagePreviewFilterEntity
import com.example.GuideExpert.data.local.models.ImagePreviewSearchEntity
import com.example.GuideExpert.data.mappers.toExcursionFilterEntity
import com.example.GuideExpert.data.mappers.toExcursionSearchEntity

@Dao
interface ExcursionFilterDao {
   // @Insert(onConflict = OnConflictStrategy.REPLACE)
   // suspend fun insertAll(excursions: List<ExcursionFilterEntity>)

    @Query("SELECT * FROM excursionFilterEntity")
    fun pagingSource(): PagingSource<Int, ExcursionFilterWithData>

    @Query("DELETE FROM excursionFilterEntity")
    suspend fun clearAll()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExcursion(excursion: List<ExcursionFilterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImagePreviewFilterEntity>)

    @Transaction
    suspend fun insertAll(excursions: List<ExcursionFilterWithData>) {
        Log.d("TAG5", "COUNT EXCURSION :: ${excursions.size}")
        insertExcursion(excursions.map{
            it.toExcursionFilterEntity()
        })
        excursions.forEach {
            // Log.d("TAG5", "EXCURSION ID :: ${it.excursion.id}")
            // Log.d("TAG5", "COUNT IMAGES :: ${it.images.size}")
            // it.images.forEach {
            //     Log.d("TAG5", "IMAGE id :: ${it.id}  url:: ${it.url}")
            // }
            insertImages(it.images)
        }
        // insertImages(images)
    }
}