package com.example.hw3_5mon

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    //https://pixabay.com/api/

    val retrofit = Retrofit.Builder().baseUrl("https://pixabay.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    val api = retrofit.create(PixaApi::class.java)
}