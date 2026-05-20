package dk.vixo.kor.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import dk.vixo.kor.domain.agent.AgentManager

class HomeComponent(
    private val agentManager: AgentManager,
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    val agents = agentManager.sessions

    init {
        lifecycle.doOnDestroy {
            agentManager.stopAll()
        }
    }

    fun newAgent(name: String) {
        agentManager.new(name = name)
    }
}
