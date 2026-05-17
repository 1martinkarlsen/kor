package dk.vixo.kor

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import dk.vixo.kor.data.AuthRepositoryImpl
import dk.vixo.kor.data.OSRepositoryImpl
import dk.vixo.kor.data.PromptSender
import dk.vixo.kor.ui.App

fun main() {
    val promptSender = PromptSender()
    val lifecycle = LifecycleRegistry()
    val root = RootComponent(
        authRepository = AuthRepositoryImpl(promptSender = promptSender),
        osRepository = OSRepositoryImpl(promptSender = promptSender),
        componentContext = DefaultComponentContext(lifecycle = lifecycle)
    )
    lifecycle.resume()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Kor",
        ) {
            App(root)
        }
    }
}