package dk.vixo.kor.domain.agent

interface AgentSession {
    val id: String
    val name: String
    val workingDirectory: String
    fun start()
    fun stop()
}
