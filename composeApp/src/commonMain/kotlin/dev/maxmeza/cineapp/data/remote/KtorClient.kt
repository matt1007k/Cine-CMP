package dev.maxmeza.cineapp.data.remote

import dev.maxmeza.cineapp.util.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


object KtorClient {
    fun getInstance(): HttpClient = HttpClient {
       install(ContentNegotiation) {
           json(json = Json{
               ignoreUnknownKeys = true
           })
       }

        install(DefaultRequest) {
            url {
                host = "std-api-staging.coremaster.dev"
                protocol = URLProtocol.HTTPS
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }

        install(Logging)

        HttpResponseValidator {
//            handleResponseExceptionWithRequest { exception, request ->
//                val clientException = exception as? ClientRequestException ?: return@handleResponseExceptionWithRequest
//                AppLogger.i("HttpResponseValidator","${clientException.response.status}")
//            }
            validateResponse { response ->

                val error: ErrorServerResult = response.body()
                AppLogger.i("validateResponse:: ","${response.status}")
                AppLogger.i("HttpResponseValidator","$error")
                if (response.status.value == 400 || response.status.value == 401) {
                    throw Exception(error.message)
                }
//                if (error.code != 0) {
//                    throw CustomResponseException(response, "Code: ${error.code}, message: ${error.message}") as Throwable
//                }
            }
        }
    }
}

@Serializable
data class ErrorServerResult(
    val message: String
)