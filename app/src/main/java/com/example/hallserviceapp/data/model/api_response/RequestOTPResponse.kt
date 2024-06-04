package com.example.hallserviceapp.data.model.api_response

data class RequestOTPResponse(
    val referenceNo: String,
    val statusCode: String,
    val statusDetail: String,
    val version: String
)