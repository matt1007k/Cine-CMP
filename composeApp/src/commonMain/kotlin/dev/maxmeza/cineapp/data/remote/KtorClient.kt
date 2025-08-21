package dev.maxmeza.cineapp.data.remote

import dev.maxmeza.cineapp.util.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


class KtorClient(
//    private val userAppSetting: UserAppSetting
) {
//    val token = getToken()

    fun getInstance(): HttpClient = HttpClient {
       install(ContentNegotiation) {
           json(json = Json{
               ignoreUnknownKeys = true
           })
       }

        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "jfpx18d9-5002.brs.devtunnels.ms"
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

                AppLogger.i("HttpResponseValidator RESPONSE",response.body())
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

        install(Auth) {
            bearer {
                loadTokens {
                    val token = ""
                    BearerTokens(token, token)
                }

//                refreshTokens {
//                    // This is where you would call your refresh endpoint
//                    // and then update the token in your data store.
//                    val newTokens = client.post("your_refresh_endpoint")
//                    // ...
//                    newTokens?.accessToken?.let { newToken ->
//                        userDataStore.saveUserToken(newToken)
//                        BearerTokens(newToken, newToken)
//                    } ?: throw AuthenticationException()
//                }
            }
        }
    }
}

@Serializable
data class ErrorServerResult(
    val message: String
)