package dk.vixo.kor.domain.usecase

import dk.vixo.kor.domain.AuthRepository
import dk.vixo.kor.domain.OSRepository
import kotlinx.coroutines.delay

private const val TIMEOUT_SECONDS = 180
private const val WAIT_FOR_LOGIN_DELAY = 5000L

class LaunchTerminalAndWaitForAuthUseCase(
    private val osRepository: OSRepository,
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): Boolean {
        osRepository.launchTerminal()
        val loggedIn = waitForLogin()

        return loggedIn
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