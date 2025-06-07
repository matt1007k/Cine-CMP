package dev.maxmeza.cineapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform