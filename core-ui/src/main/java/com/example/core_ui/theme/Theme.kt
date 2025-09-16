package com.example.core_ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Accent,
    onPrimary = White,
    primaryContainer = Accent.copy(alpha = 0.85f),
    onPrimaryContainer = Black,
    secondary = SlateGray,
    onSecondary = White,
    tertiary = ButtonLight,
    onTertiary = White,
    background = BackgroundLight,
    onBackground = SlateGray,
    surface = BackgroundLight,
    onSurface = SlateGray
)

private val DarkColors = darkColorScheme(
    primary = Accent,
    onPrimary = White,
    primaryContainer = Accent.copy(alpha = 0.7f),
    onPrimaryContainer = White,
    secondary = Accent,
    onSecondary = Black,
    tertiary = ButtonDark,
    onTertiary = White,
    background = BackgroundDark,
    onBackground = White,
    surface = BackgroundDark,
    onSurface = White
)

@Composable
fun MovuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
