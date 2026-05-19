package dk.vixo.kor.data.agent

import dk.vixo.kor.domain.agent.AgentManager
import dk.vixo.kor.domain.agent.AgentSession

class AgentManagerImpl(
    private val agentFactory: AgentSessionFactory
) : AgentManager {

    private val sessions: MutableList<AgentSession> = mutableListOf()

    override fun new(name: String) {
        val agent = agentFactory.create()
        sessions.add(agent)

        agent.start()
    }
}
