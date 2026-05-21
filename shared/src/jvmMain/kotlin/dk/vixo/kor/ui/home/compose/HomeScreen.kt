package dk.vixo.kor.ui.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalFlexBoxApi
import androidx.compose.foundation.layout.FlexBox
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dk.vixo.kor.ui.home.HomeComponent
import dk.vixo.kor.ui.home.compose.components.AgentItem

@OptIn(ExperimentalFlexBoxApi::class)
@Composable
internal fun HomeScreen(component: HomeComponent) {
    var showDialog by remember { mutableStateOf(false) }
    val agents by component.agents.collectAsState()
    val selectedAgent by component.selectedAgent.collectAsState()
    val selectedTerminal by component.selectedTerminal.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { showDialog = true }) {
                    Text("Start agent")
                }

                FlexBox(
                    config = {
                        rowGap(8.dp)
                        columnGap(8.dp)
                    }
                ) {
                    agents.forEach { agent ->
                        AgentItem(
                            agent = agent,
                            onClick = { component.selectAgent(agent) }
                        )
                    }
                }
            }

            if (selectedAgent != null) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(520.dp)
                        .align(Alignment.CenterEnd)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                        )
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedAgent!!.name,
                                style = MaterialTheme.typography.titleSmall
                            )
                            TextButton(onClick = { component.clearSelection() }) {
                                Text("✕")
                            }
                        }

                        HorizontalDivider()

                        AgentTerminalPanel(
                            widget = selectedTerminal,
                            modifier = Modifier.weight(1f).fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        StartAgentDialog(
            onStartAgent = { agentName -> component.newAgent(agentName) },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
private fun StartAgentDialog(
    onStartAgent: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val newAgentName = rememberTextFieldState()

    Dialog(
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                TextField(state = newAgentName)

                Button(
                    enabled = newAgentName.text.isNotBlank(),
                    onClick = {
                        onStartAgent(newAgentName.text.toString())
                        newAgentName.setTextAndPlaceCursorAtEnd("")
                        onDismiss()
                    }
                ) {
                    Text("Start")
                }
            }
        }
    )
}
