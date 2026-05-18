package dk.vixo.kor

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import dk.vixo.kor.domain.AuthRepository
import dk.vixo.kor.domain.OSRepository
import dk.vixo.kor.domain.usecase.LaunchTerminalAndWaitForAuthUseCase
import dk.vixo.kor.ui.loading.LoadingComponent
import dk.vixo.kor.ui.login.LoginComponent
import kotlinx.serialization.Serializable

class RootComponent(
    private val launchTerminalAndWaitForAuthUseCase: LaunchTerminalAndWaitForAuthUseCase,
    private val authRepository: AuthRepository,
    private val osRepository: OSRepository,
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    val stack: Value<ChildStack<*, Component>> =
        childStack(
            source = navigation,
            serializer = null,
            initialConfiguration = Config.Loading,
            handleBackButton = false,
            childFactory = ::createChild
        )

    private fun createChild(config: Config, componentContext: ComponentContext): Component =
        when (config) {
            is Config.Loading -> Component.Loading(
                component = LoadingComponent(
                    launchTerminalAndWaitForAuthUseCase = launchTerminalAndWaitForAuthUseCase,
                    osRepository = osRepository,
                    authRepository = authRepository,
                    onAuthenticationSuccess = {
                        navigation.replaceAll(Config.Home)
                    },
                    navigateToLogin = {
                        navigation.replaceAll(Config.Login)
                    },
                    componentContext = componentContext
                )
            )
            is Config.Login -> Component.Login(
                component = LoginComponent(
                    launchTerminalAndWaitForAuthUseCase = launchTerminalAndWaitForAuthUseCase,
                    onAuthenticationSuccess = {
                        navigation.replaceAll(Config.Home)
                    },
                    componentContext = componentContext
                )
            )
            is Config.Home -> Component.Home
        }

    sealed class Component {
        class Loading(val component: LoadingComponent) : Component()
        object Home : Component()
        class Login(val component: LoginComponent) : Component()
    }

    @Serializable
    sealed class Config {
        @Serializable
        object Loading : Config()

        @Serializable
        object Home : Config()

        @Serializable
        object Login : Config()
    }
}
