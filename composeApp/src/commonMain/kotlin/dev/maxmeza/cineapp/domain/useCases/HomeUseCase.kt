package dev.maxmeza.cineapp.domain.useCases

import dev.maxmeza.cineapp.domain.model.User
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class HomeUseCase {
    suspend fun getDemo() = flow<Result<User>> {
        emit(
            Result.success(
                User(
                    id = "1223232",
                    fullName = "Max Meza",
                    email = "max123@gmail.com"
                )
            )
        )
    }.catch { error ->
        emit(Result.failure(error))
    }
}
