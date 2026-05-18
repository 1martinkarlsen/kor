package dk.vixo.kor.ui.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dk.vixo.kor.domain.usecase.LaunchTerminalAndWaitForAuthUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginComponent(
    private val launchTerminalAndWaitForAuthUseCase: LaunchTerminalAndWaitForAuthUseCase,
    private val onAuthenticationSuccess: () -> Unit,
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val scope = coroutineScope(Dispatchers.Main)

    fun loginClick() {
        scope.launch {
            val authenticated = launchTerminalAndWaitForAuthUseCase()
            if (authenticated) {
                onAuthenticationSuccess()
            }
        }
    }
}