package dev.maxmeza.cineapp.domain.model


data class LoginResult(
    val tokens: Tokens,
    val user: User
)


data class Tokens(
    val token: String,
    val refreshToken: String
)