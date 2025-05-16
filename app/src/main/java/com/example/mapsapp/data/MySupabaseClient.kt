package com.example.mapsapp.data

import android.R.attr.password
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.mapsapp.utils.AuthState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserSession
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
            install(Storage)
            install(Auth) {
                autoLoadFromStorage = true
            }
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
    // DELETE IMAGE
    suspend fun deleteImage(imageName: String){
        val imgName = imageName.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
        client.storage.from("images").delete(imgName)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImage(imageFile: ByteArray): String {
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val imageName = storage.from("images").upload(path = "image_${fechaHoraActual.format(formato)}.png", data = imageFile)
        return buildImageUrl(imageFileName = imageName.path)
    }

    fun buildImageUrl(imageFileName: String) = "${client.supabaseUrl}/storage/v1/object/public/images/${imageFileName}"

    // Log in

    // Funcion para registratse
    suspend fun signUpWithEmail(emailValue: String, passwordValue: String): AuthState {
        try {
            Log.d("LogeTheme","Solicitando registro en MySupaBaseClient")
            client.auth.signUpWith(Email){
                email = emailValue
                password = passwordValue
            }
            return AuthState.Authenticated
        } catch (e: Exception) {
            Log.d("LogeTheme","Algo fallo en MySupabaseClient")
            return AuthState.Error(e.localizedMessage)
        }
    }

    // Funcion para iniciar sesion
    suspend fun signInWithEmail(emailValue: String, passwordValue: String): AuthState {
        try {
            Log.d("LogeTheme","Solicitando desde MySupaBaseClient")
            client.auth.signInWith(Email) {
                email = emailValue
                password = passwordValue
            }
            return AuthState.Authenticated
        } catch (e: Exception) {
            return AuthState.Error(e.localizedMessage)
        }
    }

    // Devolvera los datos del usuario que este iniciado
    fun retrieveCurrentSession(): UserSession?{
        val session = client.auth.currentSessionOrNull()
        return session
    }

    // Actualizara la sesion para que no se cierre
    fun refreshSession(): AuthState {
        try {
            client.auth.currentSessionOrNull()
            return AuthState.Authenticated
        } catch (e: Exception) {
            return AuthState.Error(e.localizedMessage)
        }
    }






}
