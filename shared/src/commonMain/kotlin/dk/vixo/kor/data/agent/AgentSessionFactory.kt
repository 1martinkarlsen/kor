package dk.vixo.kor.data.agent

import dk.vixo.kor.domain.agent.AgentSession

interface AgentSessionFactory {
    fun create(name: String): AgentSession
}
