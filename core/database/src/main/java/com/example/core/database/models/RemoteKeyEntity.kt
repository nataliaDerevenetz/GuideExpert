package com.example.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.models.RemoteKey

@Entity("RemoteKey")
data class RemoteKeyEntity(
    @PrimaryKey val id: String,
    val nextOffset: Int,
)

fun RemoteKeyEntity.toRemoteKey() = RemoteKey(
    id = id,
    nextOffset = nextOffset
)