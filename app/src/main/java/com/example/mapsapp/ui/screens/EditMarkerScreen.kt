package com.example.mapsapp.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mapsapp.viewmodels.EditMarckerViewModel
import com.example.mapsapp.viewmodels.EditMarckerViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditMarkerScreen(id: Int, navBack :() -> Unit){
    val context = LocalContext.current
    val marckViewModel : EditMarckerViewModel = viewModel(factory = EditMarckerViewModelFactory(id))
    val titile = marckViewModel.titleMarcker.observeAsState("")
    val description = marckViewModel.descriptionMarcker.observeAsState("")
    val marker = marckViewModel.marcker.observeAsState()
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val hasChanged = marckViewModel.isChanged.observeAsState(false)
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && imageUri.value != null) {
                val stream = context.contentResolver.openInputStream(imageUri.value!!)
                bitmap.value = BitmapFactory.decodeStream(stream)
            }
        }
    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri.value = it
                val stream = context.contentResolver.openInputStream(it)
                bitmap.value = BitmapFactory.decodeStream(stream)
            }
        }
    marckViewModel.getMarcker(id)
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Column(Modifier.padding(35.dp)) {
            Log.d("IMAGEN", "${marker.value?.url}")
            Image(
                painter = rememberAsyncImagePainter("https://"+marker.value?.url ?: ""),
                contentDescription = "Imagen Marcador ${marker.value?.title ?: ""}",
                modifier = Modifier.height(215.dp).width(215.dp)
            )
            TextField(
                value = titile.value,
                onValueChange = { marckViewModel.editTitle(it) ; marckViewModel.cambioRealizado(true) },
                label = { Text(marker.value?.title ?: "") },
                modifier = Modifier.fillMaxWidth()
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = description.value,
                onValueChange = { marckViewModel.editDesciption(it) ; marckViewModel.cambioRealizado(true) },
                label = { Text(marker.value?.descripcion ?: "") },
                modifier = Modifier.fillMaxWidth()
                    .height(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(onClick = {
                    val uri = createImageUri(context)
                    imageUri.value = uri
                    launcher.launch(uri!!)
                    marckViewModel.cambioRealizado(true)
                }) {
                    Text("Abrir Cámara")
                }
                Button(onClick = {
                    pickImageLauncher.launch("image/*")
                }) {
                    Text("Foto Galeria")
                }
            }
            Row {
                if (hasChanged.value == true){
                    Button(onClick = {
                        marckViewModel.updateMarcker(bitmap.value)
                        navBack()

                    }) {
                        Text("Update")
                    }
                }
                Button(onClick = {

                    navBack()
                }) {
                    Text("<--Back--")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            bitmap.value?.let {
                Image(bitmap = it.asImageBitmap(), contentDescription = null,
                    modifier = Modifier.size(300.dp).clip(RoundedCornerShape(12.dp)),contentScale = ContentScale.Crop)
            }
        }

    }
}