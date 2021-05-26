package com.example.onedriveapi.api

import com.example.onedriveapi.api.data.OAuthToken
import com.example.onedriveapi.api.data.RequestBody
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface OneDriveApi {

    @POST("common/oauth2/v2.0/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    suspend fun getAccessToken(
        @Query("client_secret") client_secret: String,
        @Body requestBody: JsonObject
    ): Response<OAuthToken>

}