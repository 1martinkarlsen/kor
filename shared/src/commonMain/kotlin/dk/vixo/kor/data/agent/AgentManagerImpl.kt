package dk.vixo.kor.data.agent

import dk.vixo.kor.domain.agent.AgentManager
import dk.vixo.kor.domain.agent.AgentSession
import dk.vixo.kor.domain.agent.AgentSessionFactory

class AgentManagerImpl(
    private val agentFactory: AgentSessionFactory
) : AgentManager {

    private val sessions: MutableList<AgentSession> = mutableListOf()

    override fun new(name: String) {
        val agent = agentFactory.create(name = name)
        sessions.add(agent)

        agent.start()
    }
}
