package dk.vixo.kor.domain.agent

import kotlinx.coroutines.flow.Flow

interface AgentSession {
    val id: String
    val name: String
    fun start()
    fun stop()
}
