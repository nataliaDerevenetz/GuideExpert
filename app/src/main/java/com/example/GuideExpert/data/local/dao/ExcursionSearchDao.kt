package com.example.GuideExpert.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.GuideExpert.data.local.models.ExcursionSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExcursionSearchDao {
    //deleted
    @Query("SELECT * FROM excursionSearchEntity")
    fun getAllExcursion() : Flow<List<ExcursionSearchEntity>>

    //deleted
    @Insert
    fun insertAllExcursionTest(excursions: List<ExcursionSearchEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(excursions: List<ExcursionSearchEntity>)

    @Query("SELECT * FROM excursionSearchEntity")
    fun pagingSource(): PagingSource<Int, ExcursionSearchEntity>

    @Query("DELETE FROM excursionSearchEntity")
    suspend fun clearAll()

}