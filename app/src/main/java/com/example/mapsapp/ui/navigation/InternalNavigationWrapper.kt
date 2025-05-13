package com.example.mapsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mapsapp.ui.navigation.Destination.PantallaDrower
import com.example.mapsapp.ui.navigation.Destination.PantallaList
import com.example.mapsapp.ui.navigation.Destination.PantallaMap
import com.example.mapsapp.ui.navigation.Destination.PantallaPermisos
import com.example.mapsapp.ui.screens.DrowerMenuScreen
import com.example.mapsapp.ui.screens.EditMarkerScreen
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.MarckerScreen
import com.example.mapsapp.ui.screens.MarkerListScreen
import com.example.mapsapp.ui.screens.PermisosScreen
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InternalNavigationWrapper(navController: NavHostController, padding: Modifier){
    NavHost(navController, PantallaMap){
        composable<PantallaMap> {
            MapScreen(){latitude, longitude -> navController.navigate(Destination.PantallaMarcador(latitude,longitude)) }
        }

        composable<PantallaList> {
            MarkerListScreen() { id -> navController.navigate(Destination.PantallaEditMarcador(id))

            }
        }

        composable<Destination.PantallaMarcador> { backStackEntry ->
            val pantallaMarcador = backStackEntry.toRoute<Destination.PantallaMarcador>()
            MarckerScreen(pantallaMarcador.latitude, pantallaMarcador.lognitude) {
                navController.popBackStack()
            }
        }

        composable<Destination.PantallaEditMarcador> { backStackEntry ->
            val pantallaEditMarcador = backStackEntry.toRoute<Destination.PantallaEditMarcador>()
            EditMarkerScreen(pantallaEditMarcador.id){
                navController.popBackStack()
            }
        }

    }
}