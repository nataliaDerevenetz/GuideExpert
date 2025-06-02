package com.example.core.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.core.models.Image

@Entity(
    indices = [Index(value = ["excursionId"])],
    foreignKeys = [
        ForeignKey(
            entity = ExcursionsFavoriteEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("excursionId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )]
)
data class ImagePreviewFavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo val excursionId: Int,
    @ColumnInfo val url: String
)

fun ImagePreviewFavoriteEntity.toImage() = Image(
    id = id, excursionId = excursionId, url = url
)
