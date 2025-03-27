package com.example.GuideExpert.data.remote.services

import com.example.GuideExpert.data.remote.pojo.ProfilePOJO
import com.example.GuideExpert.data.remote.pojo.RemoveAvatarProfileResponsePOJO
import com.example.GuideExpert.data.remote.pojo.UpdateAvatarProfileResponsePOJO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ProfileService {

    @GET("getprofile.php")
    suspend fun getProfile(@Query("id") profileId:Int): Response<ProfilePOJO>

    @Multipart
    @POST("updateavatar.php")
    suspend fun updateAvatarProfile(
        @Part("profile_id") userId: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<UpdateAvatarProfileResponsePOJO>

    @GET("removeavatar.php")
    suspend fun removeAvatarProfile(@Query("profile_id") profileId:Int): Response<RemoveAvatarProfileResponsePOJO>

}