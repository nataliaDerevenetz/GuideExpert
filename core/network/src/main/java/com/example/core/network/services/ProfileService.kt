package com.example.core.network.services

import com.example.core.network.pojo.ProfilePOJO
import com.example.core.network.pojo.RegisterTokenDeviceResponsePOJO
import com.example.core.network.pojo.RemoveAvatarProfileResponsePOJO
import com.example.core.network.pojo.UpdateAvatarProfileResponsePOJO
import com.example.core.network.pojo.UpdateProfileResponsePOJO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.util.Date

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

    @GET("updateprofile.php")
    suspend fun updateProfile(@Query("profile_id") profileId:Int, @Query("first_name") firstName:String,
                              @Query("last_name") lastName:String, @Query("sex") sex:String?,
                              @Query("email") email:String, @Query("birthday") birthday: Date
                              ): Response<UpdateProfileResponsePOJO>

    @GET("registertokendevice.php")
    suspend fun registerTokenDevice(@Query("profile_id") profileId:Int, @Query("token_device") tokenDevice:String,
                              @Query("token_device_old") tokenDeviceOld:String? = null
    ): Response<RegisterTokenDeviceResponsePOJO>
}