package dev.maxmeza.cineapp.data.remote

import dev.maxmeza.cineapp.domain.model.Tokens
import dev.maxmeza.cineapp.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteLoginResult(
    val message: String,
    val data: RemoteLoginData
)

@Serializable
data class RemoteLoginData(
    val tokens: RemoteToken,
    val data: RemoteUser
)

@Serializable
data class RemoteToken(
    val token: String,
    @SerialName("refresh_token")
    val refreshToken: String
) {
    fun toDomain() = Tokens(
        token = token,
        refreshToken = refreshToken
    )
}

@Serializable
data class RemoteUser(
    val id: String,
    val email: String,
    val username: String
) {
    fun toDomain() = User(
        id = id,
        email = email,
        fullName = username
    )
}
