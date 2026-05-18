package dk.vixo.kor.ui.loading

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoadingScreen(component: LoadingComponent) {
    when (component) {
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
