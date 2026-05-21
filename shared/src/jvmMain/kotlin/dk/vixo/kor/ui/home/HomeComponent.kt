package dk.vixo.kor.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.jediterm.terminal.ui.JediTermWidget
import com.jediterm.terminal.ui.settings.DefaultSettingsProvider
import dk.vixo.kor.domain.agent.AgentManager
import dk.vixo.kor.domain.agent.AgentSession
import dk.vixo.kor.domain.agent.PtyConnectable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeComponent(
    private val agentManager: AgentManager,
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val scope = coroutineScope(Dispatchers.Main)

    val agents: StateFlow<List<AgentSession>> = agentManager.sessions

    private val _selectedAgent = MutableStateFlow<AgentSession?>(null)
    val selectedAgent: StateFlow<AgentSession?> = _selectedAgent.asStateFlow()

    private val _terminals = MutableStateFlow<Map<String, JediTermWidget>>(emptyMap())

    val selectedTerminal: StateFlow<JediTermWidget?> = combine(_selectedAgent, _terminals) { agent, terminals ->
        agent?.let { terminals[it.id] }
    }.stateIn(scope, SharingStarted.Eagerly, null)

    init {
        scope.launch {
            agentManager.sessions.collect { sessions ->
                val current = _terminals.value
                val sessionIds = sessions.map { it.id }.toSet()

                current.filter { it.key !in sessionIds }.values.forEach { it.close() }

                sessions
                    .filter { it.id !in current && it is PtyConnectable }
                    .forEach { session ->
                        val widget = JediTermWidget(DefaultSettingsProvider())
                        widget.createTerminalSession((session as PtyConnectable).createTtyConnector()).start()
                        _terminals.update { it + (session.id to widget) }
                    }
            }
        }

        lifecycle.doOnDestroy {
            _terminals.value.values.forEach { it.close() }
            _terminals.value = emptyMap()
            agentManager.stopAll()
            scope.cancel()
        }
    }

    fun selectAgent(agent: AgentSession) {
        _selectedAgent.value = agent
    }

    fun clearSelection() {
        _selectedAgent.value = null
    }

    fun newAgent(name: String) {
        agentManager.new(name)
    }
}
