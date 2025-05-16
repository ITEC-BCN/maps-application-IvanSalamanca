package com.example.mapsapp.ui.screens.sesion

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.AuthViewModel
import com.example.mapsapp.viewmodels.AuthViewModelFactory

@Composable
fun RegisterScreen(navToDrower:() -> Unit){
    val context = LocalContext.current
    val authViewModel : AuthViewModel = viewModel(factory = AuthViewModelFactory(SharedPreferencesHelper(context)))
    val authState = authViewModel.authState.observeAsState()
    val showError = authViewModel.showError.observeAsState()

    // Comprobamos si esta la sesion iniciada
    if(authState == AuthState.Authenticated){
        navToDrower()
    }
    else{
        if (showError.value!!) {
            val errorMessage = (authState as AuthState.Error).message
            if (errorMessage!!.contains("weak_password")) {
                Toast.makeText(context, "Password should be at least 6 characters", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "An error has ocurred", Toast.LENGTH_LONG).show()
            }
            authViewModel.errorMessageShowed()
        }
//Construim Interfície d'Usuari

    }


}