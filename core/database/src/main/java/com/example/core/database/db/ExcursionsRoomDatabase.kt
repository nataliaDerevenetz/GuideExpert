package com.example.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.database.Converters
import com.example.core.database.dao.ExcursionDataDao
import com.example.core.database.dao.ExcursionFilterDao
import com.example.core.database.dao.ExcursionSearchDao
import com.example.core.database.dao.ExcursionsFavoriteDao
import com.example.core.database.dao.FavoriteDao
import com.example.core.database.dao.ImageDao
import com.example.core.database.dao.ProfileDao
import com.example.core.database.dao.RemoteKeyDao
import com.example.core.database.models.AvatarEntity
import com.example.core.database.models.ExcursionDataEntity
import com.example.core.database.models.ExcursionFavoriteEntity
import com.example.core.database.models.ExcursionFilterEntity
import com.example.core.database.models.ExcursionSearchEntity
import com.example.core.database.models.ExcursionsFavoriteEntity
import com.example.core.database.models.ImageEntity
import com.example.core.database.models.ImagePreviewFavoriteEntity
import com.example.core.database.models.ImagePreviewFilterEntity
import com.example.core.database.models.ImagePreviewSearchEntity
import com.example.core.database.models.ProfileEntity
import com.example.core.database.models.RemoteKeyEntity


@Database(entities = [ExcursionSearchEntity::class, RemoteKeyEntity::class, ExcursionFilterEntity::class,
                    ImageEntity::class, ExcursionDataEntity::class, ImagePreviewSearchEntity::class,
                    ImagePreviewFilterEntity::class, ProfileEntity::class, AvatarEntity::class,
                    ExcursionFavoriteEntity::class, ExcursionsFavoriteEntity::class,
                    ImagePreviewFavoriteEntity::class], version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class ExcursionsRoomDatabase: RoomDatabase() {
    abstract fun excursionSearchDao() : ExcursionSearchDao
    abstract fun excursionFilterDao() : ExcursionFilterDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun imageDao(): ImageDao
    abstract fun excursionDataDao(): ExcursionDataDao
    abstract fun profileDao(): ProfileDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun excursionsFavoriteDao(): ExcursionsFavoriteDao
}