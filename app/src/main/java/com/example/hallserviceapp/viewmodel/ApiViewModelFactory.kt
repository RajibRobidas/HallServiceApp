package com.example.hallserviceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hallserviceapp.domain.repository.BdAppsApiRepository
import javax.inject.Inject

class ApiViewModelFactory (private val bdAppsApiRepository: BdAppsApiRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApiViewModel::class.java)) {
            return ApiViewModel(bdAppsApiRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}