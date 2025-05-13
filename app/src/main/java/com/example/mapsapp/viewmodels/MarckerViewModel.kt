package com.example.mapsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.MyApp
import com.example.mapsapp.data.Marcker
import com.example.mapsapp.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// Hacer boton edidted para guardar si hay cambios
class MarckerViewModel (latitude: Double,longitude : Double) : ViewModel(){
    val repository = Repository()

    val cordenadas = latitude.toString()+";"+longitude

    // Valores Marcador
    private val _titleMarker = MutableLiveData<String>()
    val titleMarcker = _titleMarker

    private val _descriptionMarker = MutableLiveData<String>()
    val descriptionMarcker = _descriptionMarker

    private val _url = MutableLiveData<String>()
    val url = _url

     fun insertNewMarcker(cordenadas : String, title: String, desc: String, url : String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertMarcker(Marcker(id = null,cordenadas,title,desc,url))
        }
    }

    fun getMarcker (id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getMarcker(id)
        }
    }

    // Insterar nuvo marcador
     fun saveMarcker(){
        CoroutineScope(Dispatchers.IO).launch {
            var marcker = Marcker(id = 0,cordenadas,_titleMarker.value!!,descriptionMarcker.value!!,"URL Test")
            repository.insertMarcker(marcker)
        }
    }

    fun editTitle(newTitle: String){
        _titleMarker.value = newTitle
    }

    fun editDesciption(newDescriptor: String){
        _descriptionMarker.value = newDescriptor
    }

    fun editImage(newImage : String){
        _url.value = newImage
    }








}


