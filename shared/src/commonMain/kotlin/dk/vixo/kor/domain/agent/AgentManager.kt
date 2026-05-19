package dk.vixo.kor.domain.agent

interface AgentManager {

    fun new(name: String)
    fun stopAll()
}
