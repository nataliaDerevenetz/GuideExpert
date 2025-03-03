package com.example.GuideExpert.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.GuideExpert.data.local.dao.ExcursionDataDao
import com.example.GuideExpert.data.local.dao.ExcursionFilterDao
import com.example.GuideExpert.data.local.dao.ExcursionSearchDao
import com.example.GuideExpert.data.local.dao.ImageDao
import com.example.GuideExpert.data.local.dao.ImagePreviewSearchDao
import com.example.GuideExpert.data.local.dao.RemoteKeyDao
import com.example.GuideExpert.data.local.models.ExcursionDataEntity
import com.example.GuideExpert.data.local.models.ExcursionSearchEntity
import com.example.GuideExpert.data.local.models.ExcursionFilterEntity
import com.example.GuideExpert.data.local.models.ImageEntity
import com.example.GuideExpert.data.local.models.ImagePreviewSearchEntity
import com.example.GuideExpert.data.local.models.RemoteKeyEntity


@Database(entities = [ExcursionSearchEntity::class, RemoteKeyEntity::class, ExcursionFilterEntity::class,
                     ImageEntity::class, ExcursionDataEntity::class, ImagePreviewSearchEntity::class], version = 1,exportSchema = false)
abstract class ExcursionsRoomDatabase: RoomDatabase() {
    abstract fun excursionSearchDao() : ExcursionSearchDao
    abstract fun excursionFilterDao() : ExcursionFilterDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun imageDao(): ImageDao
    abstract fun excursionDataDao(): ExcursionDataDao
    abstract fun imagePreviewSearchDao(): ImagePreviewSearchDao
}