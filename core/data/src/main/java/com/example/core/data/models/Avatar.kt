package com.example.core.data.models

import com.example.core.database.models.AvatarEntity
import com.example.core.models.Avatar
import com.example.core.network.pojo.AvatarPOJO
import com.example.core.network.pojo.UpdateAvatarProfileResponsePOJO

fun AvatarPOJO.toAvatar() =
    Avatar(id = id, profileId = profileId, url = url)

fun Avatar.toAvatarEntity() =
    AvatarEntity(id = id!!, profileId = profileId, url = url)

fun AvatarEntity.toAvatar() =
    Avatar(id = id, profileId = profileId, url = url)

fun UpdateAvatarProfileResponsePOJO.toAvatar(profileId:Int) =
    Avatar(id = idAvatar!!, profileId = profileId, url = imageUrl!!)
