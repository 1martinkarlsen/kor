package dk.vixo.kor.ui.loading

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dk.vixo.kor.domain.AuthRepository
import dk.vixo.kor.domain.OSRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TIMEOUT_SECONDS = 180
private const val WAIT_FOR_LOGIN_DELAY = 5000L

class LoadingComponent(
    private val authRepository: AuthRepository,
    private val osRepository: OSRepository,
    private val onAuthenticationSuccess: () -> Unit,
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val scope = coroutineScope(Dispatchers.Main)

    init {
        scope.launch {
            authenticate()
        }
    }

    private suspend fun authenticate() {
        val isAuthenticated = authRepository.isAuthenticated()
        if (isAuthenticated) {
            onAuthenticationSuccess()
        } else {
            osRepository.launchTerminal()
            val loggedIn = waitForLogin()
            if (loggedIn) {
                onAuthenticationSuccess()
            }
        }
    }

    private suspend fun waitForLogin(): Boolean {
        val deadline = System.currentTimeMillis() + TIMEOUT_SECONDS * 1000
        while (System.currentTimeMillis() < deadline) {
            if (authRepository.isAuthenticated()) {
                return true
            }

            delay(WAIT_FOR_LOGIN_DELAY)
        }

        return false
    }
}