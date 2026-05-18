package dk.vixo.kor.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Config {
    @Serializable
    object Loading : Config()

    @Serializable
    object Home : Config()

    @Serializable
    object Login : Config()
}