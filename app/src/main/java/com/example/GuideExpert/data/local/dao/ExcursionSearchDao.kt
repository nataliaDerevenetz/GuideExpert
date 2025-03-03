package com.example.GuideExpert.data.local.dao

import android.util.Log
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.GuideExpert.data.local.models.ExcursionDataEntity
import com.example.GuideExpert.data.local.models.ExcursionSearchEntity
import com.example.GuideExpert.data.local.models.ExcursionSearchWithData
import com.example.GuideExpert.data.local.models.ImageEntity
import com.example.GuideExpert.data.local.models.ImagePreviewSearchEntity
import com.example.GuideExpert.data.mappers.toExcursionSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExcursionSearchDao {
    //deleted
    @Query("SELECT * FROM excursionSearchEntity")
    fun getAllExcursion() : Flow<List<ExcursionSearchEntity>>

    //deleted
    @Insert
    fun insertAllExcursionTest(excursions: List<ExcursionSearchEntity>)


  //  @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  suspend fun insertAll(excursions: List<ExcursionSearchEntity>)

    @Query("SELECT * FROM excursionSearchEntity")
    fun pagingSource(): PagingSource<Int, ExcursionSearchWithData>

    @Query("DELETE FROM excursionSearchEntity")
    suspend fun clearAll()



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExcursion(excursion: List<ExcursionSearchEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImagePreviewSearchEntity>)

    @Transaction
    suspend fun insertAll(excursions: List<ExcursionSearchWithData>) {
        Log.d("TAG5", "COUNT EXCURSION :: ${excursions.size}")
        insertExcursion(excursions.map{
            it.toExcursionSearchEntity()
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