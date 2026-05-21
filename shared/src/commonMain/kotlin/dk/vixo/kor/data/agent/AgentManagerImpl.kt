package dk.vixo.kor.data.agent

import dk.vixo.kor.domain.agent.AgentManager
import dk.vixo.kor.domain.agent.AgentSession
import dk.vixo.kor.domain.agent.AgentSessionFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AgentManagerImpl(
    private val agentFactory: AgentSessionFactory
) : AgentManager {

    private val _sessions = MutableStateFlow<List<AgentSession>>(emptyList())
    override val sessions: StateFlow<List<AgentSession>> = _sessions.asStateFlow()

    override fun new(name: String) {
        val agent = agentFactory.create(name = name)
        agent.start()
        _sessions.update { it + agent }
    }

    override fun stopAll() {
        _sessions.value.forEach { it.stop() }
        _sessions.value = emptyList()
    }
}
