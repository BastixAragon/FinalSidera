package com.example.sidera_app


data class ApodResponse(
    val title: String,
    val date: String,
    val explanation: String,
    val url: String,
    val media_type: String
)
