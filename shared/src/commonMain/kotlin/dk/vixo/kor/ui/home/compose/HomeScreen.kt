package dk.vixo.kor.ui.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalFlexBoxApi
import androidx.compose.foundation.layout.FlexBox
import androidx.compose.foundation.layout.FlexBoxConfig
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dk.vixo.kor.ui.home.HomeComponent
import dk.vixo.kor.ui.home.compose.components.AgentItem

@OptIn(ExperimentalFlexBoxApi::class)
@Composable
fun HomeScreen(component: HomeComponent) {
    var showDialog by remember { mutableStateOf(false) }
    val newAgentName = rememberTextFieldState()

    val agents by component.agents.collectAsState(initial = emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            Button(
                onClick = {
                    showDialog = true
                },
                content = {
                    Text(text = "Start agent")
                }
            )

            FlexBox(
                config = {
                    rowGap(4.dp)
                    columnGap(8.dp)
                },
                content = {
                    agents.forEach { agent ->
                        AgentItem(agent)
                    }
                }
            )
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            },
            content = {
                Column(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                ) {
                    TextField(
                        state = newAgentName
                    )

                    Button(
                        onClick = {
                            component.newAgent(newAgentName.text.toString())
                            newAgentName.setTextAndPlaceCursorAtEnd("")
                            showDialog = false
                        },
                        content = {
                            Text("Start")
                        }
                    )
                }
            }
        )
    }
}
