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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

/**
 * Gestiona la edicion de los marcadores y obtiene el marcador con ¿id? para mostrar
 */
class EditMarckerViewModel( id : Int) : ViewModel() {

    val repository = Repository()

    // Marcador que se ha seleccionado para editar
    private val _marcker = MutableLiveData<Marcker>()
    val marcker = _marcker

    // Valores Marcador
    private val _titleMarker = MutableLiveData<String>()
    val titleMarcker = _titleMarker

    private val _descriptionMarker = MutableLiveData<String>()
    val descriptionMarcker = _descriptionMarker

    private val _url = MutableLiveData<String>()
    val url = _url

    // Muestra su hay algun cambio
    private val _isChanged = MutableLiveData<Boolean>(false)
    val isChanged = _isChanged


    init {
        getMarcker(id)
    }
    //
    fun getMarcker (id: Int){
        var marcador : Marcker
        CoroutineScope(Dispatchers.IO).launch {
             marcador = repository.getMarcker(id)
            CoroutineScope(Dispatchers.Main).launch {
                _marcker.value = marcador
            }
        }
    }

    // cambiar valores
    fun cambioRealizado( cambio : Boolean){
        _isChanged.value = cambio
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateMarcker(img : Bitmap?){
        CoroutineScope(Dispatchers.IO).launch {
            // Eliminamos foto antigua
            repository.deleteImage(_marcker.value!!.url!!)


            // Actualicamos marcador nuevo
            val stream = ByteArrayOutputStream()
            img?.compress(Bitmap.CompressFormat.PNG, 0, stream)
            val imgURL = repository.uploadImage(stream.toByteArray())
            var marcker = Marcker(id = 0,_marcker.value!!.cordenadas,_titleMarker.value!!,descriptionMarcker.value!!,imgURL)
            repository.updateMarcker(_marcker.value!!.id!!,marcker)

        }
    }


    // Funciones para Modificar el nuevo marcador
    fun editTitle(newTitle: String){
        _titleMarker.value = newTitle
    }

    fun editDesciption(newDescriptor: String){
        _descriptionMarker.value = newDescriptor
    }
}