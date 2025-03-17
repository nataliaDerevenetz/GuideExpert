package com.example.GuideExpert.data.remote.services

import com.example.GuideExpert.data.remote.pojo.ProfilePOJO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileService {

    @GET("getprofile.php")
    suspend fun getProfile(@Query("id") profileId:Int): Response<ProfilePOJO>

}