package com.example.core.network.services

import com.example.core.network.pojo.ConfigPOJO
import com.example.core.network.pojo.ProfileYandexPOJO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DataSourceService {
    @GET("config.json")
    suspend fun getConfig(): Response<ConfigPOJO>

    @GET("loginyandex.php")
    suspend fun loginYandex(@Query("oauth_token") oauthToken:String): Response<ProfileYandexPOJO>

}