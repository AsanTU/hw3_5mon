package com.example.hw3_5mon

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaApi {

    @GET("api/")
    fun getImages(
        @Query("q") keyWord: String,
        @Query("key") key: String = "41575137-b6467c524551223e0e396b2e0",
        @Query("page") page: Int,
        @Query("per_page") perPage : Int = 3
    ): Call<PixaModel>
}