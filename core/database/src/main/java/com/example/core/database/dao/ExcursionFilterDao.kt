package com.example.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.core.database.models.ExcursionFilterEntity
import com.example.core.database.models.ExcursionFilterWithData
import com.example.core.database.models.ImagePreviewFilterEntity
import com.example.core.database.models.toExcursionFilterEntity

@Dao
interface ExcursionFilterDao {

    @Transaction
    @Query("SELECT * FROM excursionFilterEntity ORDER BY idSort")
    fun pagingSource(): PagingSource<Int, ExcursionFilterWithData>

    @Query("DELETE FROM excursionFilterEntity")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExcursion(excursion: List<ExcursionFilterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImagePreviewFilterEntity>)

    @Transaction
    suspend fun insertAll(excursions: List<ExcursionFilterWithData>) {
        insertExcursion(excursions.map{ it.toExcursionFilterEntity() })
        excursions.forEach { insertImages(it.images) }
    }
}