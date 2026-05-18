package dk.vixo.kor.ui.loading

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
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

    private val _state = MutableValue<LoadingState>(LoadingState.Loading)
    val state: Value<LoadingState> = _state

    init {
        retry()
    }

    fun retry() {
        scope.launch {
            authenticate()
        }
    }

    private suspend fun authenticate() {
        _state.update { LoadingState.Loading }

        val isInstalled = osRepository.isInstalled()
        if (!isInstalled) {
            // Claude is not installed
            _state.update { LoadingState.Error }
            return
        }

        val isAuthenticated = authRepository.isAuthenticated()
        if (isAuthenticated) {
            println("Is authenticated")
            onAuthenticationSuccess()
        } else {
            println("Is not authenticated")
            val authenticated = launchTerminalAndWaitForAuthUseCase()
            if (authenticated) {
                onAuthenticationSuccess()
            } else {
                navigateToLogin()
            }
        }
    }

    sealed class LoadingState {
        object Loading : LoadingState()
        object Error : LoadingState()
    }
}
