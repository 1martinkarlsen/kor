package dk.vixo.kor.data

import dk.vixo.kor.domain.OSRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OSRepositoryImpl(
    private val promptSender: PromptSender,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : OSRepository {

    override suspend fun isInstalled(): Boolean = withContext(dispatcher) {
        // Version will throw error if claude code is not installed
        val isInstalled = promptSender.sendAndGetTermination("--version")
        if (!isInstalled) {
            return@withContext false
        }

        return@withContext true
    }

    override suspend fun launchTerminal() = withContext(dispatcher) {
        promptSender.runCommand("osascript", "-e", """tell app "Terminal" to do script "claude login"""")
        Unit
    }
}