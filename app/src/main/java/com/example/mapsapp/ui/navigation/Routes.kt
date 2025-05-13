package com.example.mapsapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destination{
    @Serializable
    object PantallaPermisos : Destination()

    @Serializable
    object PantallaDrower : Destination()

    @Serializable
    object PantallaMap: Destination()

    @Serializable
    object PantallaList: Destination()

    @Serializable
    data class PantallaMarcador (val latitude : Double, val lognitude : Double) : Destination()

    @Serializable
    data class PantallaEditMarcador ( val id : Int) : Destination()
}



