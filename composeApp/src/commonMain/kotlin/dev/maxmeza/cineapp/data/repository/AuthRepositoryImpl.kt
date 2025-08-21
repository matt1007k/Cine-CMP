package dev.maxmeza.cineapp.data.repository

import dev.maxmeza.cineapp.data.service.AuthService
import dev.maxmeza.cineapp.domain.model.LoginResult
import dev.maxmeza.cineapp.domain.repository.AuthRepository
import dev.maxmeza.cineapp.data.mappers.toDomain

class AuthRepositoryImpl(
    val authService: AuthService,
): AuthRepository {
    override suspend fun login(email: String, password: String): Result<LoginResult> {
        val result = authService.login(email, password)
        return if(result.isSuccess) {
            Result.success(result.getOrThrow().toDomain())
        } else {
            Result.failure(result.exceptionOrNull()!!)
        }
    }
}