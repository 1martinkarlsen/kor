package dk.vixo.kor.data.agent

import dk.vixo.kor.domain.agent.AgentSession
import java.util.UUID

class AgentSessionFactoryImpl : AgentSessionFactory {

    override fun create(name: String): AgentSession {
        return ClaudeAgentSessionImpl(
            id = UUID.randomUUID().toString(),
            name = name,
            workingDirectory = ""
        )
    }
}
