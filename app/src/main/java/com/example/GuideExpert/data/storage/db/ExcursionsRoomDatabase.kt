package com.example.GuideExpert.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.GuideExpert.data.storage.dao.ExcursionDao
import com.example.GuideExpert.data.storage.models.ExcursionEntity


@Database(entities = [ExcursionEntity::class], version = 1)
abstract class ExcursionsRoomDatabase: RoomDatabase() {
    abstract fun excursionDao() : ExcursionDao
}