package com.example.hallserviceapp.data.repository

import android.util.Log
import com.example.hallserviceapp.api.BdAppsApi
import com.example.hallserviceapp.data.model.api_response.RequestOTPResponse
import com.example.hallserviceapp.data.model.api_response.VerifyOTPResponse
import com.example.hallserviceapp.domain.repository.BdAppsApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class BdAppsApiRepositoryImpl: BdAppsApiRepository {

    private val api: BdAppsApi = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
    .create(BdAppsApi::class.java)
    override suspend fun requestOTP(subscriberId: String): Flow<Response<RequestOTPResponse>> =
        flow {
            emit(api.requestOTP(subscriberId))
        }.catch {
            Log.d(TAG, "requestOTP: ${it.message}")
        }

    override suspend fun verifyOTP(
        referenceNo: String,
        otp: String
    ): Flow<Response<VerifyOTPResponse>> = flow {
        emit(api.verifyOTP(referenceNo, otp))
    }.catch {
        Log.d(TAG, "verifyOTP: ${it.message}")
    }


    companion object {
        private const val TAG = "BdAppsApiRepositoryImpl"
        private const val BASE_URL = "http://20.197.50.116:10399/"
    }

}