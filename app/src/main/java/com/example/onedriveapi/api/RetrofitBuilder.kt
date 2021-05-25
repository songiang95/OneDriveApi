package com.example.onedriveapi.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://login.microsoftonline.com/"
    const val CLIENT_ID = "8d3b85c1-6b69-47ca-9973-f9af3dcd4a75"
    const val CLIENT_SECRET = "68Yos2mlBZMo~3m.7kd_MWW68dE-td2CEp"
    const val SCOPE = "offline_access%20files.read.all"
    const val REDIRECT_URI = "https://login.microsoftonline.com/common/oauth2/nativeclient"
    const val LOGIN_URL =
        "https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=$CLIENT_ID&scope=$SCOPE&response_type=code&redirect_uri=$REDIRECT_URI"

    private fun getRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val oneDriveApi: OneDriveApi = getRetrofit().create(OneDriveApi::class.java)

}