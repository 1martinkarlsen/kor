package dk.vixo.kor.domain.usecase

import dk.vixo.kor.domain.AuthRepository
import dk.vixo.kor.domain.OSRepository
import kotlinx.coroutines.delay

private const val TIMEOUT_SECONDS = 180
private const val WAIT_FOR_LOGIN_DELAY = 5000L
private const val MILLIS_PER_SECOND = 1000

class LaunchTerminalAndWaitForAuthUseCase(
    private val osRepository: OSRepository,
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): Boolean {
        osRepository.launchTerminal()
        return waitForLogin()
    }

    private suspend fun waitForLogin(): Boolean {
        val deadline = System.currentTimeMillis() + TIMEOUT_SECONDS * MILLIS_PER_SECOND
        while (System.currentTimeMillis() < deadline) {
            if (authRepository.isAuthenticated()) {
                return true
            }

            delay(WAIT_FOR_LOGIN_DELAY)
        }

        return false
    }
}
