package dk.vixo.kor.domain.agent

interface AgentSession {
    val id: String
    val workingDirectory: String
    fun start()
    fun stop()
}
