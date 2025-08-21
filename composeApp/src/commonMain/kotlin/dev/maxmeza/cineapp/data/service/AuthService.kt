package dev.maxmeza.cineapp.data.service

import dev.maxmeza.cineapp.data.remote.RemoteLoginData
import dev.maxmeza.cineapp.data.remote.RemoteLoginResult
import dev.maxmeza.cineapp.util.AppLogger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val email: String,
    val password: String
)

class AuthService(
    private val client: HttpClient
) {

    suspend fun login(email: String, password: String): Result<RemoteLoginData> {
        AppLogger.i("LOGIN FORM DATA","$email $password")
       return try {
           val result = client.post("api/v1/users/login") {
               setBody(LoginDto(email = email, password = password))
           }.body<RemoteLoginResult>()
           AppLogger.i("LOGIN DATA", result.toString())
           Result.success(result.data)
       } catch (e: Exception) {
           AppLogger.i("LOGIN","ERROR LOGIN::::" + e.toString())
           Result.failure(e)
       }


    }

}
