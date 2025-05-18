package com.example.mapsapp.viewmodels

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.data.Marcker
import com.example.mapsapp.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

/**
 * Creacion de marcadores
 */
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


    // Insterar nuvo marcador
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveMarcker(img : Bitmap?){
        val stream = ByteArrayOutputStream()
        img?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        CoroutineScope(Dispatchers.IO).launch {
            val imgURL = repository.uploadImage(stream.toByteArray())
            var marcker = Marcker(id = 0,cordenadas,_titleMarker.value!!,descriptionMarcker.value!!,imgURL)
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


