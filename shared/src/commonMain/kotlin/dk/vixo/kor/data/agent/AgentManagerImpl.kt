package dk.vixo.kor.data.agent

import dk.vixo.kor.domain.agent.AgentManager
import dk.vixo.kor.domain.agent.AgentSession
import dk.vixo.kor.domain.agent.AgentSessionFactory
import java.util.Collections

class AgentManagerImpl(
    private val agentFactory: AgentSessionFactory
) : AgentManager {

    private val sessions: MutableList<AgentSession> = Collections.synchronizedList(mutableListOf())

    override fun new(name: String) {
        val agent = agentFactory.create(name = name)
        sessions.add(agent)

        agent.start()
    }

    override fun stopAll() {
        sessions.forEach { it.stop() }
        sessions.clear()
    }
}
