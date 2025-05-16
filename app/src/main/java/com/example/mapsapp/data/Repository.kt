package com.example.mapsapp.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
    suspend fun deleteImage(imageName : String) = database.deleteImage(imageName)
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImage (img : ByteArray) = database.uploadImage(img)
    suspend fun updateMarcker(id: Int, editedMarker: Marcker){
        deleteMarcker(id)
        insertMarcker(editedMarker)
    }

}