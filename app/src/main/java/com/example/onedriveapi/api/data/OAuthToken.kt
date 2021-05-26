package com.example.onedriveapi.api.data

import com.google.gson.annotations.SerializedName

data class OAuthToken(
    val token_type: String,
    val scope: String,
    val expires_in: Int,
    val ext_expires_in: Int,
    val access_token: String,
    val refresh_token: String
)