package dev.maxmeza.cineapp.data.service

import dev.maxmeza.cineapp.data.remote.RemoteLoginResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ApiService(private val httpClient: HttpClient) {
    suspend fun login(email: String, password: String): Result<RemoteLoginResult> {
        return try {
            val response = httpClient.post("api/v1/users/login") {
                setBody(LoginDto(email, password))
            }.body<RemoteLoginResult>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}