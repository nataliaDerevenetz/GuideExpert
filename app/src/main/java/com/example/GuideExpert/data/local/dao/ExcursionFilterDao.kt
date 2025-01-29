package com.example.GuideExpert.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.GuideExpert.data.local.models.ExcursionEntity

@Dao
interface ExcursionFilterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<ExcursionEntity>)

    @Query("SELECT * FROM excursionEntity")
    fun pagingSource(): PagingSource<Int, ExcursionEntity>

    @Query("DELETE FROM excursionEntity")
    suspend fun clearAll()
}