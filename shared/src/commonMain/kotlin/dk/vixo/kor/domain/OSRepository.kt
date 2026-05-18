package dk.vixo.kor.domain

interface OSRepository {

    suspend fun isInstalled(): Boolean

    suspend fun launchTerminal()
}
