package com.example.mapsapp

import android.app.Activity
import android.app.Application
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.data.MySupabaseClient
import com.example.mapsapp.utils.PermissionStatus
import com.example.mapsapp.viewmodels.PermissionViewModel

class MyApp: Application() {
    companion object {
        lateinit var database: MySupabaseClient
    }
    override fun onCreate() {
        super.onCreate()
        database = MySupabaseClient(
            supabaseUrl = "https://wsgjmndrewvratmybddu.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndzZ2ptbmRyZXd2cmF0bXliZGR1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDY2MDAzMzEsImV4cCI6MjA2MjE3NjMzMX0.ijfskp6-c1iIvLFjfbHEHB-blmuVLzPNRnLwTfgN_30"
        )
    }
}

