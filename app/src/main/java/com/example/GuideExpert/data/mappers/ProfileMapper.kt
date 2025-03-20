package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.local.models.ImageEntity
import com.example.GuideExpert.data.local.models.ProfileEntity
import com.example.GuideExpert.data.remote.pojo.ProfilePOJO
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.domain.models.Profile
import java.util.Date

fun ProfileEntity.toProfile() = Profile(
    id = id,login=login,realName=realName,firstName=firstName,lastName=lastName,sex=sex,email=email,birthday=birthday,phone=phone,avatarId=avatarId
)

fun ProfilePOJO.toProfile():Profile {
    var _birthday = birthday
    if (birthday.time < -2202202619) _birthday = Date(6786181)
   return Profile(
        id = id,
        login = login,
        realName = realName,
        firstName = firstName,
        lastName = lastName,
        sex = sex,
        email = email,
        birthday = _birthday,
        phone = phone,
        avatarId = avatarId
    )
}

fun Profile.toProfileEntity() = ProfileEntity(
    id = id,login=login,realName=realName,firstName=firstName,lastName=lastName,sex=sex,email=email,birthday=birthday,phone=phone,avatarId=avatarId
)