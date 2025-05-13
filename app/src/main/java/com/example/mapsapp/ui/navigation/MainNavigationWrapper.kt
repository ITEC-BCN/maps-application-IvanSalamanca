package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mapsapp.ui.navigation.Destination.PantallaDrower

import com.example.mapsapp.ui.navigation.Destination.PantallaPermisos
import com.example.mapsapp.ui.screens.DrowerMenuScreen
import com.example.mapsapp.ui.screens.PermisosScreen

@Composable
fun MainNavigationWrapper(navController: NavHostController){
    NavHost(navController, PantallaPermisos){
        composable<PantallaPermisos> {
            PermisosScreen(){-> navController.navigate(PantallaDrower) }
        }

        composable<PantallaDrower> {
            DrowerMenuScreen()
        }
    }
}