package dk.vixo.kor.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dk.vixo.kor.RootComponent
import dk.vixo.kor.ui.home.compose.HomeScreen
import dk.vixo.kor.ui.loading.LoadingScreen
import dk.vixo.kor.ui.login.LoginScreen
import dk.vixo.kor.ui.theme.KorTheme

@Composable
fun App(
    rootComponent: RootComponent
) {
    KorTheme {
        Children(
            stack = rootComponent.stack,
            animation = stackAnimation(fade())
        ) {
            when (val instance = it.instance) {
                is RootComponent.Component.Loading -> LoadingScreen(component = instance.component)
                is RootComponent.Component.Login -> LoginScreen(component = instance.component)
                is RootComponent.Component.Home -> HomeScreen(component = instance.component)
            }
        }
    }
}
