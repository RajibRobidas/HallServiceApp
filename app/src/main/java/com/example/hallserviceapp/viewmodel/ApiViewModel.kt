package com.example.hallserviceapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hallserviceapp.data.model.api_response.RequestOTPResponse
import com.example.hallserviceapp.data.model.api_response.VerifyOTPResponse
import com.example.hallserviceapp.domain.repository.BdAppsApiRepository
import com.example.hallserviceapp.domain.rules.UserValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ApiViewModel(
    private val bdAppsApiRepository: BdAppsApiRepository
) : ViewModel() {

    private val _requestOTPResponse = MutableStateFlow<RequestOTPResponse?>(null)
    val requestOTPResponse = _requestOTPResponse.asStateFlow()

    private val _verifyOTPResponse = MutableStateFlow<VerifyOTPResponse?>(null)
    val verifyOTPResponse = _verifyOTPResponse.asStateFlow()
    fun validatePhoneNo(phoneNo: String): String? {
        return UserValidator.validatePhoneNo(phoneNo)
    }


    fun requestOTP(phoneNo: String) = viewModelScope.launch {
        val subscriberId = if (phoneNo[0] == '+' && phoneNo[1] == '8' && phoneNo[2] == '8') {
            phoneNo.substring(3)
        } else if (phoneNo[0] == '8' && phoneNo[1] == '8') {
            phoneNo.substring(2)
        } else {
            phoneNo
        }
        Log.d(TAG, "requestOTP: number: $subscriberId")
        bdAppsApiRepository.requestOTP(subscriberId = subscriberId).collectLatest {
            val response = it
            if (response.isSuccessful) {
                _requestOTPResponse.value = response.body()
                Log.d(TAG, "requestOTP: ${response.body()}")
            } else {
                Log.d(TAG, "requestOTP: error: ${response.errorBody()}")
            }
        }


    }


    fun verifyOTP(otp: String) = viewModelScope.launch {
        requestOTPResponse.value?.let { response ->

            bdAppsApiRepository.verifyOTP(
                referenceNo = response.referenceNo,
                otp = otp
            ).collectLatest {
                val verifyResponse = it
                if (verifyResponse.isSuccessful) {
                    _verifyOTPResponse.value = verifyResponse.body()

                } else {
                    Log.d(TAG, "verifyOTP: error: ${verifyResponse.errorBody()}")
                }
            }


        }
    }


    companion object {
        const val TAG = "PreferenceViewModel"
    }
}