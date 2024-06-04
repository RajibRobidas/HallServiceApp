package com.example.hallserviceapp.api

import com.example.hallserviceapp.data.model.api_response.RequestOTPResponse
import com.example.hallserviceapp.data.model.api_response.VerifyOTPResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface BdAppsApi {


    @POST(REQUEST_OTP_END_POINT)
    suspend fun requestOTP(@Query(SUBSCRIBER_ID) number: String): Response<RequestOTPResponse>

    @POST(VERIFY_OTP_END_POINT)
    suspend fun verifyOTP(
        @Query(value = REFERENCE_NO) referenceNo: String,
        @Query(value = OTP) otp: String
    ): Response<VerifyOTPResponse>



    companion object {
        private const val REQUEST_OTP_END_POINT = "request_otp"
        private const val VERIFY_OTP_END_POINT = "verify_otp"

        private const val REFERENCE_NO = "referenceNo"
        private const val OTP = "otp"
        private const val SUBSCRIBER_ID = "subscriberId"
    }
}