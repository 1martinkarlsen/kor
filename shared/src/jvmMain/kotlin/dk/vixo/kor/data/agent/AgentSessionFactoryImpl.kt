package dk.vixo.kor.data.agent

import dk.vixo.kor.domain.agent.AgentSession
import dk.vixo.kor.domain.agent.AgentSessionFactory
import java.util.UUID

class AgentSessionFactoryImpl : AgentSessionFactory {

    override fun create(name: String): AgentSession {
        return ClaudeAgentSession(
            id = UUID.randomUUID().toString(),
            name = name,
            workingDirectory = ""
        )
    }
}
