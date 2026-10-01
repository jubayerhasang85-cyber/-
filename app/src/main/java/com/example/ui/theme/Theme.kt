package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = MadrasaGreenPrimary,
    onPrimary = TextOnGreen,
    primaryContainer = MadrasaGreenContainer,
    onPrimaryContainer = TextOnGreen,
    secondary = MadrasaGreenMedium,
    onSecondary = TextOnGreen,
    secondaryContainer = Color(0xFFE8F5E9),
    onSecondaryContainer = MadrasaGreenDark,
    tertiary = MadrasaGold,
    onTertiary = TextDark,
    background = MadrasaBgLight,
    onBackground = TextDark,
    surface = MadrasaSurfaceWhite,
    onSurface = TextDark,
    surfaceVariant = Color(0xFFEFF3EF),
    onSurfaceVariant = TextDark,
    outline = MadrasaCardBorder,
    error = StatusAbsent,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = MadrasaGreenLight,
    onPrimary = TextOnGreen,
    primaryContainer = MadrasaGreenPrimary,
    onPrimaryContainer = TextOnGreen,
    secondary = MadrasaGreenMedium,
    onSecondary = TextOnGreen,
    background = Color(0xFF101411),
    onBackground = Color(0xFFE2E3DF),
    surface = Color(0xFF191D1A),
    onSurface = Color(0xFFE2E3DF),
    surfaceVariant = Color(0xFF222823),
    onSurfaceVariant = Color(0xFFC2C8C0),
    outline = Color(0xFF3E4840)
)

@Composable
fun MazharulUloomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
