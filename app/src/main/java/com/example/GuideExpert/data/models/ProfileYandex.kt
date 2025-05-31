package com.example.GuideExpert.data.models

import com.example.GuideExpert.data.remote.pojo.ProfileYandexPOJO
import com.example.GuideExpert.domain.models.ProfileYandex

fun ProfileYandexPOJO.toProfileYandex() = ProfileYandex(id,auth_token,time)
