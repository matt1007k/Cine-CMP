package dev.maxmeza.cineapp.domain.useCases

import dev.maxmeza.cineapp.domain.model.LoginResult
import dev.maxmeza.cineapp.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUseCases(private val authRepository: AuthRepository) {
    suspend fun invoke(email: String, password: String) = flow<Result<LoginResult>> {
        emit(authRepository.login(email = email, password = password))
    }.catch { error ->
        emit(Result.failure(error))
    }.flowOn(Dispatchers.IO)
}