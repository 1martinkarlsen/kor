package dk.vixo.kor.domain

interface OSRepository {

    suspend fun launchTerminal()
}