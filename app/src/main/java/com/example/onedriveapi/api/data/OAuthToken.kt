package com.example.onedriveapi.api.data

data class OAuthToken(
    val token_type: String,
    val expires_in: Int,
    val scope: String,
    val access_token: String,
    val refresh_token: String
)