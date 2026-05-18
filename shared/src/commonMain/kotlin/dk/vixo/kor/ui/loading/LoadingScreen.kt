package dk.vixo.kor.ui.loading

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun LoadingScreen(component: LoadingComponent) {
    val state by component.state.subscribeAsState()

    when (state) {
        LoadingComponent.LoadingState.Loading -> LoadingIndicator()
        LoadingComponent.LoadingState.Error -> {
            Column {
                Text(text = "It looks like claude is not installed.")
                Button(
                    onClick = component::retry,
                    content = {
                        Text(text = "Retry")
                    }
                )
            }
        }
    }
}
