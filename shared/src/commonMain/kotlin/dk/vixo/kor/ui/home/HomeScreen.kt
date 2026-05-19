package dk.vixo.kor.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
fun HomeScreen(component: HomeComponent) {
    var showDialog by remember { mutableStateOf(false) }
    var newAgentName by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            onClick = {
                showDialog = true
            },
            content = {
                Text(text = "Start agent")
            }
        )
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            },
            content = {
                Column {
                    TextField(
                        value = newAgentName,
                        onValueChange = {
                            newAgentName = it
                        }
                    )

                    Button(
                        onClick = {
                            component.newAgent(newAgentName)
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