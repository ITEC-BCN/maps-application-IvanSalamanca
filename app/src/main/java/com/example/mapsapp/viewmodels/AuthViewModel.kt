package com.example.mapsapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapsapp.MyApp
import com.example.mapsapp.data.MySupabaseClient
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.utils.SharedPreferencesHelper
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.user.UserSession
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch

class AuthViewModel(private val sharedPreferences: SharedPreferencesHelper) : ViewModel(){
    private val authManager = MyApp.database
    private val _email = MutableLiveData<String>()
    val email = _email
    private val _password = MutableLiveData<String>()
    val password = _password
    private val _authState = MutableLiveData<AuthState>()
    val authState = _authState
    private val _showError = MutableLiveData<Boolean>(false)
    val showError = _showError
    private val _user = MutableLiveData<String?>()
    val user = _user

    init {
        checkExistingSession()
    }

    private fun checkExistingSession() {
        viewModelScope.launch {
            val accessToken = sharedPreferences.getAccessToken()
            val refreshToken = sharedPreferences.getRefreshToken()
            when {
                !accessToken.isNullOrEmpty() -> refreshToken()
                !refreshToken.isNullOrEmpty() -> refreshToken()
                else -> _authState.value = AuthState.Unauthenticated
            }
        }
    }

    fun editEmail(value: String) {
        _email.value = value
    }

    fun editPassword(value: String) {
        _password.value = value
    }

    fun errorMessageShowed(){
        _showError.value = false
    }

    fun signUp() {
        viewModelScope.launch {
            Log.d("LogeTheme","Solicitando en viewModel register")
            _authState.value = authManager.signUpWithEmail(_email.value!!, _password.value!!)
            if (_authState.value is AuthState.Error) {
                _showError.value = true
            } else {
                val session = authManager.retrieveCurrentSession()
                sharedPreferences.saveAuthData(
                    session!!.accessToken,
                    session.refreshToken
                )
            }
        }
    }

    fun signIn() {
        viewModelScope.launch {
            Log.d("LogeTheme","En Funcion ViewModel")
            _authState.value = authManager.signInWithEmail(_email.value!!, _password.value!!)
            if (_authState.value is AuthState.Error) {
                Log.d("LogeTheme","ViewModel Recive un fallo")
                _showError.value = true
            } else {
                val session = authManager.retrieveCurrentSession()
                sharedPreferences.saveAuthData(
                    session!!.accessToken,
                    session.refreshToken
                )
            }
        }
    }


    private fun refreshToken() {
        viewModelScope.launch {
            try {
                authManager.refreshSession()
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                sharedPreferences.clear()
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sharedPreferences.clear()
            _authState.value = AuthState.Unauthenticated
        }
    }




}
