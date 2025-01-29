package com.example.GuideExpert.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.GuideExpert.data.local.dao.ExcursionDao
import com.example.GuideExpert.data.local.dao.ExcursionFilterDao
import com.example.GuideExpert.data.local.dao.RemoteKeyDao
import com.example.GuideExpert.data.local.models.ExcursionEntity
import com.example.GuideExpert.data.local.models.ExcursionFilterEntity
import com.example.GuideExpert.data.local.models.RemoteKeyEntity


@Database(entities = [ExcursionEntity::class, RemoteKeyEntity::class, ExcursionFilterEntity::class], version = 1,exportSchema = false)
abstract class ExcursionsRoomDatabase: RoomDatabase() {
    abstract fun excursionDao() : ExcursionDao
    abstract fun excursionFilterDao() : ExcursionFilterDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}