package com.example.GuideExpert.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.GuideExpert.data.local.models.ExcursionFilterEntity

@Dao
interface ExcursionFilterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<ExcursionFilterEntity>)

    @Query("SELECT * FROM excursionFilterEntity")
    fun pagingSource(): PagingSource<Int, ExcursionFilterEntity>

    @Query("DELETE FROM excursionFilterEntity")
    suspend fun clearAll()
}