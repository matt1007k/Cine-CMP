package dev.maxmeza.cineapp.domain.repository

import dev.maxmeza.cineapp.domain.model.LoginResult

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<LoginResult>
}