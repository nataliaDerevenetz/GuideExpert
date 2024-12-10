package com.example.GuideExpert.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.GuideExpert.data.local.dao.ExcursionDao
import com.example.GuideExpert.data.local.models.ExcursionEntity


@Database(entities = [ExcursionEntity::class], version = 1,exportSchema = false)
abstract class ExcursionsRoomDatabase: RoomDatabase() {
    abstract fun excursionDao() : ExcursionDao
}