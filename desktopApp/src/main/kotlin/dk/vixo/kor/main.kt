package dk.vixo.kor

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import dk.vixo.kor.data.AuthRepositoryImpl
import dk.vixo.kor.data.OSRepositoryImpl
import dk.vixo.kor.data.PromptSender
import dk.vixo.kor.domain.usecase.LaunchTerminalAndWaitForAuthUseCase
import dk.vixo.kor.ui.App

fun main() {
    val promptSender = PromptSender()

    val authRepo = AuthRepositoryImpl(promptSender = promptSender)
    val osRepo = OSRepositoryImpl(promptSender = promptSender)

    application {
        val lifecycle = remember { LifecycleRegistry() }
        val root = remember {
            RootComponent(
                launchTerminalAndWaitForAuthUseCase = LaunchTerminalAndWaitForAuthUseCase(
                    osRepository = osRepo,
                    authRepository = authRepo
                ),
                authRepository = authRepo,
                osRepository = osRepo,
                componentContext = DefaultComponentContext(lifecycle = lifecycle)
            )
        }

        LaunchedEffect(lifecycle) {
            lifecycle.resume()
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Kor",
        ) {
            App(root)
        }
    }
}
