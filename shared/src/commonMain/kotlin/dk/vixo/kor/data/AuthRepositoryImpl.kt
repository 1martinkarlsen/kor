package dk.vixo.kor.data

import dk.vixo.kor.domain.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val promptSender: PromptSender,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {

    override suspend fun isAuthenticated(): Boolean = withContext(dispatcher) {
        val response = promptSender.send("hi")
        if (response?.contains("Not logged in") == true) {
            return@withContext false
        }

        return@withContext true
    }
}
