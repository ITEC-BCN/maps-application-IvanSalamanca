package com.example.mapsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditMarckerViewModelFactory (private val id : Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditMarckerViewModel::class.java)) {
            return EditMarckerViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}