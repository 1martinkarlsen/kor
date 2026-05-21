package dk.vixo.kor.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KorColorScheme = darkColorScheme(
    primary = Color(0xFF2F81F7),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF1C2D41),
    onPrimaryContainer = Color(0xFFADCCF7),
    secondary = Color(0xFF57606A),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFF21262D),
    onSecondaryContainer = Color(0xFFCDD9E5),
    tertiary = Color(0xFF3FB950),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFF122416),
    onTertiaryContainer = Color(0xFF82E09A),
    error = Color(0xFFF85149),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFF2D1616),
    onErrorContainer = Color(0xFFFFBFBF),
    background = Color(0xFF0D1117),
    onBackground = Color(0xFFE6EDF3),
    surface = Color(0xFF0D1117),
    onSurface = Color(0xFFE6EDF3),
    surfaceVariant = Color(0xFF161B22),
    onSurfaceVariant = Color(0xFF8B949E),
    outline = Color(0xFF30363D),
    outlineVariant = Color(0xFF21262D),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFFFFFFF),
    inverseOnSurface = Color(0xFF0D1117),
    inversePrimary = Color(0xFF1A5EB8),
    surfaceTint = Color(0xFF2F81F7),
)

@Composable
fun KorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KorColorScheme,
        content = content
    )
}
