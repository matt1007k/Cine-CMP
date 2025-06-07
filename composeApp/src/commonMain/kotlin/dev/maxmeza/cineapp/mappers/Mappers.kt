package dev.maxmeza.cineapp.mappers

import dev.maxmeza.cineapp.data.remote.RemoteLoginData
import dev.maxmeza.cineapp.domain.model.LoginResult


fun RemoteLoginData.toDomain() = LoginResult(
    tokens = tokens.toDomain(),
    user = data.toDomain()
)
