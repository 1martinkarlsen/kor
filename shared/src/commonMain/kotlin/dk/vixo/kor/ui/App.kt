package dk.vixo.kor.ui

import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dk.vixo.kor.RootComponent
import dk.vixo.kor.ui.login.LoginScreen

@Composable
fun App(
    rootComponent: RootComponent
) {
    Children(
        stack = rootComponent.stack,
        animation = stackAnimation(fade())
    ) {
        when (val instance = it.instance) {
            is Component.Loading -> {
                LoadingIndicator()
            }
            is Component.Login -> LoginScreen(component = instance.component)
            is Component.Home -> {
                Text(text = "Home")
            }
        }
    }
}