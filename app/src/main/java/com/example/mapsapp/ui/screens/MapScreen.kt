package com.example.mapsapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.data.Marcker
import com.example.mapsapp.viewmodels.ListMarckViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(navigateToDetail: (Double, Double) -> Unit){

    val mapsViewModel : ListMarckViewModel = viewModel<ListMarckViewModel>()
    val marckerList = mapsViewModel.marckerList.observeAsState(emptyList<Marcker>())

    // Colocamos la camara centrada en el ITB
    val itb = LatLng(41.4534225, 2.1837151)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(itb, 13f)
    }
    GoogleMap(  // Creacion del mapa
        Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapLongClick = {
            navigateToDetail(it.latitude,it.longitude)
        }
    ){
        Log.d("Ivan","Entro al bucle")
        marckerList.value.fastForEach { marker -> // Colocamos los marcadores en su sitio
            var cordenadasList = marker.cordenadas.split(";")
            Log.d("Ivan", "En el bucle ${marker.cordenadas}")
            Marker(
                state = MarkerState(position = LatLng(cordenadasList[0].toDouble(),cordenadasList[1].toDouble())),
                title = marker.title,
                snippet = marker.descripcion
            )
        }
        Log.d("Ivan", "Salgo del bucle")
    }

}


