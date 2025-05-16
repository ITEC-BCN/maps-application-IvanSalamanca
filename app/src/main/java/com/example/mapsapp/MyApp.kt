package com.example.mapsapp

import android.app.Application
import com.example.mapsapp.data.MySupabaseClient

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

