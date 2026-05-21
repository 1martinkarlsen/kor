package dk.vixo.kor.ui.home.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import com.jediterm.terminal.ui.JediTermWidget

@Composable
internal fun AgentTerminalPanel(widget: JediTermWidget?, modifier: Modifier = Modifier) {
    if (widget != null) {
        key(widget) {
            SwingPanel(
                modifier = modifier,
                factory = { widget }
            )
        }
    } else {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text(
                text = "Select an agent",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
