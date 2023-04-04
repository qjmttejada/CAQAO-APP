package com.example.caqao.network

import com.squareup.moshi.Json

data class UserToken (
    @Json(name = "access_token") val accessToken: String?,
    val status: Int,
)