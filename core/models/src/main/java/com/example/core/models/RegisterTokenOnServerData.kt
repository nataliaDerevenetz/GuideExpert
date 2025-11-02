package com.example.core.models

data class RegisterTokenOnServerData(
    val id: Int,
    val authToken: String,
    val deviceToken:String
)
