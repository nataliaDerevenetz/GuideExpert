package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.local.models.ImageEntity
import com.example.GuideExpert.data.local.models.ProfileEntity
import com.example.GuideExpert.data.remote.pojo.ProfilePOJO
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.domain.models.Profile

fun ProfileEntity.toProfile() = Profile(
    id = id,login=login,realName=realName,firstName=firstName,lastName=lastName,sex=sex,email=email,birthday=birthday,phone=phone,avatarId=avatarId
)
fun ProfilePOJO.toProfile() = Profile(id = id,login=login,realName=realName,firstName=firstName,lastName=lastName,sex=sex,email=email,birthday=birthday,phone=phone,avatarId=avatarId
)

fun Profile.toProfileEntity() = ProfileEntity(
    id = id,login=login,realName=realName,firstName=firstName,lastName=lastName,sex=sex,email=email,birthday=birthday,phone=phone,avatarId=avatarId
)