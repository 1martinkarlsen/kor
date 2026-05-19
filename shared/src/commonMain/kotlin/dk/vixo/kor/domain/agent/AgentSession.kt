package dk.vixo.kor.domain.agent

import kotlinx.coroutines.flow.Flow

interface AgentSession {
    val id: String
    val name: String
    val output: Flow<String>
    fun start()
    fun stop()
}
