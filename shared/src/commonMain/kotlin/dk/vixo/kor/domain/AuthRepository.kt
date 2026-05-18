package dk.vixo.kor.domain

interface AuthRepository {

    suspend fun isAuthenticated(): Boolean
}
