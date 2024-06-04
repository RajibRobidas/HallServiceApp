package com.example.hallserviceapp.domain.rules

import android.util.Log

object UserValidator {

    fun validatePhoneNo(phoneNo: String): String? {
        var result: String? = null

        val bangladeshiPhoneNumberPattern = "^(?:\\+88|88)?01[3-9]\\d{8}$".toRegex()
        if (!phoneNo.matches(bangladeshiPhoneNumberPattern)) {
            result = "Invalid Phone No"
        }
        Log.d(TAG, "validatePhoneNo: phoneNo: $phoneNo, result: $result")
        return result
    }

    private const val TAG = "UserValidator"
}