package dk.vixo.kor.ui.loading

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dk.vixo.kor.domain.AuthRepository
import dk.vixo.kor.domain.OSRepository
import dk.vixo.kor.domain.usecase.LaunchTerminalAndWaitForAuthUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadingComponent(
    private val launchTerminalAndWaitForAuthUseCase: LaunchTerminalAndWaitForAuthUseCase,
    private val authRepository: AuthRepository,
    private val osRepository: OSRepository,
    private val onAuthenticationSuccess: () -> Unit,
    private val navigateToLogin: () -> Unit,
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val scope = coroutineScope(Dispatchers.Main)

    init {
        scope.launch {
            authenticate()
        }
    }

    private suspend fun authenticate() {
        val isInstalled = osRepository.isInstalled()
        if (!isInstalled) {
            // Claude is not installed
            return
        }

        val isAuthenticated = authRepository.isAuthenticated()
        if (isAuthenticated) {
            onAuthenticationSuccess()
        } else {
            val authenticated = launchTerminalAndWaitForAuthUseCase()
            if (authenticated) {
                onAuthenticationSuccess()
            } else {
                navigateToLogin()
            }
        }
    }
}