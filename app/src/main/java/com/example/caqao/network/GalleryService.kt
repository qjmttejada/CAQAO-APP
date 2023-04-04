package com.example.caqao.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET

private const val BASE_URL = "http://fvea.pythonanywhere.com"
//private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GalleryService {
    @GET("photos")
    suspend fun getPhotos(): List<GalleryPhoto>
}

object GalleryApi {
    val retrofitService : GalleryService by lazy {
        retrofit.create(GalleryService::class.java)
    }
}