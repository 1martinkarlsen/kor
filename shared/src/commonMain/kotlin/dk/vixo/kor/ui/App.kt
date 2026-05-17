package dk.vixo.kor.ui

import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.*
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dk.vixo.kor.RootComponent

@Composable
fun App(
    rootComponent: RootComponent
) {
    Children(
        stack = rootComponent.stack,
        animation = stackAnimation(fade())
    ) {
        when (it.instance) {
            is RootComponent.Component.Loading -> {
                LoadingIndicator()
            }
            is RootComponent.Component.Login -> {}
            is RootComponent.Component.Home -> {}
        }
    }
}