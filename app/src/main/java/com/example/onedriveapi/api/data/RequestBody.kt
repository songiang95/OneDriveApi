package com.example.onedriveapi.api.data

data class RequestBody(
    val client_id: String,
    val redirect_uri: String,
    val code: String,
    val grant_type: String
)