package com.example.mapsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MarckerViewModelFactory(private val latitude: Double, private val longitude : Double) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarckerViewModel::class.java)) {
            return MarckerViewModel(latitude,longitude) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}