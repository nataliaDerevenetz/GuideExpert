package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.models.RemoteKeyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RemoteKeyEntity)

    @Query("SELECT * FROM RemoteKey WHERE id = :id")
    fun getById(id: String): Flow<RemoteKeyEntity?>

    @Query("DELETE FROM RemoteKey WHERE id = :id")
    suspend fun deleteById(id: String)
}