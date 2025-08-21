package dev.maxmeza.cineapp.util

import dev.maxmeza.cineapp.Greeting

val platformName = Greeting().greet().lowercase()

fun getOSLogo(): String = when {
    platformName.contains("ios") -> OSEnum.IOS.getOSLogo()
    platformName.contains("android") -> OSEnum.ANDROID.getOSLogo()
    platformName.contains("java") || platformName.contains("desktop") -> OSEnum.DESKTOP.getOSLogo()
    platformName.contains("web") -> OSEnum.WEB.getOSLogo()
    else -> OSEnum.ANDROID.getOSLogo()
}

enum class OSEnum {
    IOS,
    DESKTOP,
    ANDROID,
    WEB;

    fun getOSName(): String {
        return when (this) {
            IOS -> "iOS"
            DESKTOP -> "Desktop"
            ANDROID -> "Android"
            WEB -> "Web"
        }
    }

    fun getOSLogo(): String {
        return when (this) {
            IOS -> "https://img.icons8.com/?size=100&id=30840&format=png&color=000000"
            DESKTOP -> "https://img.icons8.com/?size=100&id=zHqiFyDG4Zcy&format=png&color=000000"
            ANDROID -> "https://img.icons8.com/?size=100&id=P2AnGyiJxMpp&format=png&color=000000"
            WEB -> "https://img.icons8.com/?size=100&id=63807&format=png&color=000000"
        }
    }
}