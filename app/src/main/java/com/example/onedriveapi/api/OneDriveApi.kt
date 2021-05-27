package com.example.onedriveapi.api

import com.example.onedriveapi.api.data.Root
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OneDriveApi {

    @GET("drive/{path}/children")
    suspend fun getDriveChildren(@Path("path") path: String): Response<Root>

}