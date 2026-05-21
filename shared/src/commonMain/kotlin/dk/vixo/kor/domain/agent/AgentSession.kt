package dk.vixo.kor.domain.agent

interface AgentSession {
    val id: String
    val name: String
    fun start()
    fun stop()
}
