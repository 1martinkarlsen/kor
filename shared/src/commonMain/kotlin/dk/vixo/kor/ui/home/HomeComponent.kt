package dk.vixo.kor.ui.home

import com.arkivanov.decompose.ComponentContext
import dk.vixo.kor.domain.agent.AgentManager

class HomeComponent(
    private val agentManager: AgentManager,
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    fun newAgent(name: String) {
        agentManager.new()
    }
}
