package com.example.GuideExpert.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.GuideExpert.data.local.models.ExcursionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExcursionDao {
    @Query("SELECT * FROM excursionEntity")
    fun getAllExcursion() : Flow<List<ExcursionEntity>>

    @Insert
    fun insertAllExcursionTest(excursions: List<ExcursionEntity>)

}