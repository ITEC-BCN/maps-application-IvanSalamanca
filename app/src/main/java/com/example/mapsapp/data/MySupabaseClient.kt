package com.example.mapsapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MySupabaseClient() {
    lateinit var client: SupabaseClient
    lateinit var storage: Storage
    constructor(supabaseUrl: String, supabaseKey: String): this(){
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest)
        }
        storage = client.storage
    }
    //SQL operations
    // SELECT ALL
    suspend fun getAllMarckers(): List<Marcker> {
        return client.from("Marckers").select().decodeList<Marcker>()
    }

    // SELECT WHERE ID
    suspend fun getMarcker(id: Int): Marcker{
        return client.from("Marckers").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<Marcker>()
    }
    
    // INSERT 
    suspend fun insertMarcker(marker: Marcker){
        client.from("Marckers").insert(marker)
    }
    // UPDATE
    suspend fun updateMarcker(id: Int, codrdenadas: String, title: String, descripcion : String, url : String){
        client.from("Marckers").update({
            set("cordenadas", codrdenadas)
            set("title", title)
            set("descripcion", descripcion)
            set("url", url)
        }) { filter { eq("id", id) } }
    }
    // DELLETE
    suspend fun deleteMarcker(id: Int){
        client.from("Marckers").delete{ filter { eq("id", id) } }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImage(imageFile: ByteArray): String {
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val imageName = storage.from("images").upload(path = "image_${fechaHoraActual.format(formato)}.png", data = imageFile)
        return buildImageUrl(imageFileName = imageName.path)
    }

    fun buildImageUrl(imageFileName: String) = "${client.supabaseUrl}/storage/v1/object/public/images/${imageFileName}"




}
