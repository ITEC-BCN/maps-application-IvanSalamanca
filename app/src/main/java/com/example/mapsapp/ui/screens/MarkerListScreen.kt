package com.example.mapsapp.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.data.Marcker
import com.example.mapsapp.viewmodels.ListMarckViewModel

@Composable
fun MarkerListScreen(navigateToEdit : (Int) -> Unit){
    val viewModel : ListMarckViewModel = viewModel<ListMarckViewModel>()
    val lista = viewModel.marckerList.observeAsState(emptyList<Marcker>())

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 110.dp),
        horizontalAlignment = Alignment.CenterHorizontally,){
        LazyColumn() {

            items(lista.value) { item ->
                val dissmissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            viewModel.deleteMarker(item.id!!)
                            true
                        } else {
                            false
                        }
                    }
                )
                SwipeToDismissBox(state = dissmissState, backgroundContent = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red,
                            modifier= Modifier.padding(bottom = 15.dp)
                        )
                    }
                }){
                    Log.d("Ivan","Elemento de la lista ${item.title}")
                    MarckerItemList(item,navigateToEdit)
                }

            }

        }
    }
}

@Composable
fun MarckerItemList(marcker: Marcker,navigateToEdit : (Int) -> Unit){
    Card(
        modifier = Modifier.padding(8.dp),
        onClick = {navigateToEdit(marcker.id!!)},

        ) {
        Box(){
            Row(modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth()
            ) {
                Column (){
                    Text(
                        text = marcker.title,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize(),
                        fontSize = 20.sp,

                    )
                    Text(
                        text = marcker.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize(),
                        fontSize = 20.sp,

                    )
                }
            }
        }
    }
}

