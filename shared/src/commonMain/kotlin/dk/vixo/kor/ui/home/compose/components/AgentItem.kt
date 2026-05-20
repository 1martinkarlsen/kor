package dk.vixo.kor.ui.home.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dk.vixo.kor.domain.agent.AgentSession

@Composable
fun AgentItem(agent: AgentSession) {
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
        Text(text = agent.name)
    }
}