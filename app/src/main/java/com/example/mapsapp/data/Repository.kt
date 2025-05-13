package com.example.mapsapp.data

import android.util.Log
import com.example.mapsapp.MyApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// necesario ?

// TODO Es necesario?
class Repository {

    val database = MyApp.database

    suspend fun insertMarcker(newMarcker: Marcker) = database.insertMarcker(newMarcker)
    suspend fun getMarcker(id : Int) = database.getMarcker(id)
    suspend fun getAllMarckers() = database.getAllMarckers()
    suspend fun deleteMarcker (id: Int)= database.deleteMarcker(id)
    suspend fun updateMarcker(id: Int, editedMarker: Marcker){
        deleteMarcker(id)
        insertMarcker(editedMarker)
    }

}