package dk.vixo.kor.ui.login

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(
    component: LoginComponent
) {
    Button(
        onClick = component::loginClick,
        content = {
            Text(text = "Login")
        }
    )
}
