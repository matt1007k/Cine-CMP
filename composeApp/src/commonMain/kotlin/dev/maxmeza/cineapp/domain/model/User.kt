package dev.maxmeza.cineapp.domain.model

data class User(
    val id: String,
    val fullName: String,
    val email: String
) {
    val firstName: String
        get() = "#${fullName.split(" ").first()}}"
}