package com.example.hallserviceapp.data.model.api_response

data class VerifyOTPResponse(
    val statusCode: String,
    val statusDetail: String,
    val subscriberId: String,
    val subscriptionStatus: String,
    val version: String
)