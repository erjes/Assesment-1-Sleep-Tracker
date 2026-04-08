package com.radjamahesaw0054.assesment1.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = TealPrimary,
    secondary = TealSecondary,
    background = TealBackground,
    surface = TealSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF00201D),
    onSurface = Color(0xFF00201D),
    error = Color(0xFFBA1A1A),
    surfaceVariant = Color(0xFFDAEBE8),
    onSurfaceVariant = Color(0xFF3F4947)
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkTealPrimary,
    secondary = DarkTealSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color(0xFF00302A),
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color(0xFFFFB4AB),
    surfaceVariant = Color(0xFF003731),
    onSurfaceVariant = Color(0xFFB0C4C1)
)

@Composable
fun Assesment1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}