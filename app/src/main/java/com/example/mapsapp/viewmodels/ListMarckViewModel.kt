package com.example.mapsapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.data.Marcker
import com.example.mapsapp.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListMarckViewModel : ViewModel() {

    val repository = Repository()

    private val _marckerList = MutableLiveData<List<Marcker>>()
    val marckerList = _marckerList

    init {
        getAllMarckers()
    }

    // Recive todos los marcadores que hay en la BBDD
    fun getAllMarckers() {
        Log.d("Ivan","Entro en GetALL marquers")
        CoroutineScope(Dispatchers.IO).launch {
            val response : List<Marcker> = repository.getAllMarckers()
            withContext(Dispatchers.Main) {
                _marckerList.value = response
            }
        }
        Log.d("Ivan","Salgo de get all marckers")
    }

    // Eliminar marcador
    fun deleteMarker(id : Int){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteMarcker(id)
        }
    }
}