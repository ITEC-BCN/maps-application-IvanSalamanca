package com.example.mapsapp.ui.screens.sesion

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.AuthViewModel
import com.example.mapsapp.viewmodels.AuthViewModelFactory

@Composable
fun RegisterScreen(navToDrower:() -> Unit){ // Pantalla para realizar el registro
    val context = LocalContext.current
    val authViewModel : AuthViewModel = viewModel(factory = AuthViewModelFactory(SharedPreferencesHelper(context)))
    val email = authViewModel.email.observeAsState("")
    val password = authViewModel.password.observeAsState("")
    val authState = authViewModel.authState.observeAsState()
    val showError = authViewModel.showError.observeAsState()

    // Comprobamos si esta la sesion iniciada
    if(authState == AuthState.Authenticated){
        navToDrower()
    }
    else{
        if (showError.value!!) {
            val errorMessage = (authState.value as AuthState.Error).message
            if (errorMessage.contains("invalid_credentials")) {
                Toast.makeText(context, "Invalid credentials", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "An error has ocurred", Toast.LENGTH_LONG).show()
                Log.d("Ivan",errorMessage.toString())
            }
            authViewModel.errorMessageShowed()
        }
        Column(     // Empieza la vista
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Registro Usuario", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(16.dp))

            TextField(      // Email
                value = email.value,
                onValueChange = { authViewModel.editEmail(it) },
                label = { Text("E-Mail") },
                modifier = Modifier.fillMaxWidth()
                    .height(120.dp)
            )

            TextField(      // Password
                value = password.value,
                onValueChange = { authViewModel.editPassword(it) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(Modifier.height(16.dp))
            Button(     // Boton para registrarse
                onClick = {
                    navToDrower()
                    Log.d("LogeTheme","BotonPulsado");authViewModel.signUp()
                    if(authState == AuthState.Authenticated){
                        navToDrower()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrate")
            }
        }
    }
}