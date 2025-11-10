package com.example.sidera_app

import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {
    //traer 5 entradas aleatorias
    @GET("planetary/apod")
    suspend fun getApods(
        @Query("count") count: Int = 5,
        @Query("thumbs") thumbs: Boolean = true
    ): List<ApodResponse>
}
