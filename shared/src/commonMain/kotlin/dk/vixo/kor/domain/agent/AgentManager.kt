package dk.vixo.kor.domain.agent

import kotlinx.coroutines.flow.StateFlow

interface AgentManager {

    val sessions: StateFlow<List<AgentSession>>
    fun new(name: String)
    fun stopAll()
}
