package com.example.sidera_app

import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {
    //traer 5 entradas aleatorias
    @GET("planetary/apod")
    suspend fun getApods(
        @Query("count") count: Int = 5,
        @Query("api_key") apiKey: String = "79D6KcO7VjadT5KEZVVKMSSBpv9RKPU0NoZiWInh"
    ): List<ApodResponse>
}
