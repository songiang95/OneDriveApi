package com.example.onedriveapi.api

import com.example.onedriveapi.PreferencesHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val AUTH_URL = "https://login.microsoftonline.com/"
    private const val DRIVE_URL = "https://graph.microsoft.com/v1.0/"
    const val CLIENT_ID = "8d3b85c1-6b69-47ca-9973-f9af3dcd4a75"
    const val CLIENT_SECRET = "68Yos2mlBZMo~3m.7kd_MWW68dE-td2CEp"
    const val SCOPE = "offline_access%20files.read.all"
    const val REDIRECT_URI = "https://login.microsoftonline.com/common/oauth2/nativeclient"
    const val LOGIN_URL =
        "https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=$CLIENT_ID&scope=$SCOPE&response_type=code&redirect_uri=$REDIRECT_URI"

    const val ONEDRIVE_ROOT_PATH = "root"

    private fun getAuthRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AUTH_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getDriveRetrofit(): Retrofit {
        val client = OkHttpClient.Builder().addInterceptor {
            val newRequest = it.request()
                .newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer ${PreferencesHelper.getAccessToken()}"
                ).build()

            it.proceed(newRequest)
        }.build()

        return Retrofit.Builder()
            .baseUrl(DRIVE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authOneDriveApi: AuthOneDriveApi = getAuthRetrofit().create(AuthOneDriveApi::class.java)
    val oneDriveApi: OneDriveApi = getDriveRetrofit().create(OneDriveApi::class.java)

}