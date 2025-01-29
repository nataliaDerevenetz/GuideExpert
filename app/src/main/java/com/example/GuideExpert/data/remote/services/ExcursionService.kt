package com.example.GuideExpert.data.remote.services

import com.example.GuideExpert.data.remote.pojo.ExcursionPOJO
import com.example.GuideExpert.data.remote.pojo.ExcursionsPagingPOJO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExcursionService {
    //  @Headers("User-Agent: Your-App-Name")
    @GET("excursions.json")
    suspend fun getExcursions(): Response<List<ExcursionPOJO>>

    @GET("server.php")
    suspend fun getExcursionsSearchPaging(@Query("offset") offset:Int, @Query("limit") limit:Int): Response<ExcursionsPagingPOJO>

    @GET("server.php")
    suspend fun getExcursionsFiltersPaging(@Query("offset") offset:Int, @Query("limit") limit:Int): Response<ExcursionsPagingPOJO>

}