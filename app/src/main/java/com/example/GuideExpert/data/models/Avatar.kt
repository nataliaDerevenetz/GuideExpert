package com.example.GuideExpert.data.models

import com.example.GuideExpert.data.local.models.AvatarEntity
import com.example.GuideExpert.data.remote.pojo.AvatarPOJO
import com.example.GuideExpert.data.remote.pojo.UpdateAvatarProfileResponsePOJO
import com.example.GuideExpert.domain.models.Avatar

fun AvatarPOJO.toAvatar() =  Avatar(id = id, profileId = profileId, url = url)

fun Avatar.toAvatarEntity() =  AvatarEntity(id = id!!, profileId = profileId, url = url)

fun AvatarEntity.toAvatar() =  Avatar(id = id, profileId = profileId, url = url)

fun UpdateAvatarProfileResponsePOJO.toAvatar(profileId:Int) = Avatar(id=idAvatar!!, profileId = profileId, url = imageUrl!!)
