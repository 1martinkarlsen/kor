package dk.vixo.kor

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dk.vixo.kor.domain.AuthRepository
import dk.vixo.kor.domain.OSRepository
import dk.vixo.kor.ui.home.HomeComponent
import dk.vixo.kor.ui.loading.LoadingComponent
import dk.vixo.kor.ui.login.LoginComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class RootComponent(
    private val authRepository: AuthRepository,
    private val osRepository: OSRepository,
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val scope = coroutineScope(Dispatchers.Main)
    private val navigation = StackNavigation<Config>()

    val stack: Value<ChildStack<*, Component>> =
        childStack(
            source = navigation,
            serializer = null,
            initialConfiguration = Config.Loading,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(config: Config, componentContext: ComponentContext): Component =
        when (config) {
            is Config.Loading -> Component.Loading(
                component = LoadingComponent(
                    osRepository = osRepository,
                    authRepository = authRepository,
                    onAuthenticationSuccess = {
                        navigation.replaceAll(Config.Home)
                    },
                    componentContext = componentContext
                )
            )
            is Config.Login -> Component.Login
            is Config.Home -> Component.Home
        }

    sealed class Component {
        class Loading(component: LoadingComponent) : Component()
        object Home : Component()
        object Login : Component()
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