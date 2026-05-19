package dk.vixo.kor.domain.agent

interface AgentSessionFactory {
    fun create(name: String): AgentSession
}
