package com.example.onedriveapi.api

import com.example.onedriveapi.api.data.OAuthToken
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface OneDriveApi {

    @POST("common/oauth2/v2.0/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun getAccessToken(
        @Query("client_id") client_id: String,
        @Query("redirect_uri") redirect_uri: String,
        @Query("client_secret") client_secret: String,
        @Query("code") code: String,
        @Query("grant_type") grant_type: String = "authorization_code"
    ): Call<Response<OAuthToken>>

}