package com.ress.usstatesdata

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder().baseUrl("https://datausa.io/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val usStatesService = retrofit.create(ApiService::class.java)

interface ApiService{
    @GET("data?drilldowns=State&measures=Population&year=latest")
    suspend fun getDatas():DatasResponse //suspend => concurrency, takutnya lag, inet lama dll
}

private val retrofitPixabay = Retrofit.Builder()
    .baseUrl("https://pixabay.com/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val pixabayService = retrofitPixabay.create(PixabayApiService::class.java)

interface PixabayApiService {
    @GET("?image_type=photo&per_page=3")
    suspend fun searchImagesURL(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): PixabayResponse
}