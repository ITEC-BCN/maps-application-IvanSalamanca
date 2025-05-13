package com.example.mapsapp.data

import kotlinx.serialization.Serializable

@Serializable
data class Marcker(
    val id : Int? = 0,
    val cordenadas : String,
    val title : String,
    val descripcion : String,
    val url : String
)