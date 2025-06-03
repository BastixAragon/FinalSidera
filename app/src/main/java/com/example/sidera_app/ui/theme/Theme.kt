package com.example.sidera_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define tus colores astronómicos aquí mismo si no están en colors.xml
private val SpaceBlue = Color(0xFF1A237E)  // Azul espacial
private val NebulaPurple = Color(0xFF4A148C) // Púrpura de nebulosa
private val StarWhite = Color(0xFFFFFFFF)  // Blanco estelar
private val CometGray = Color(0xFF9E9E9E)  // Gris cometario

private val DarkColorScheme = darkColorScheme(
    primary = SpaceBlue,
    secondary = NebulaPurple,
    tertiary = StarWhite,
    background = Color(0xFF121212),  // Fondo oscuro espacial
    surface = Color(0xFF1E1E1E),     // Superficie oscura
    onPrimary = StarWhite,
    onSecondary = StarWhite,
    onBackground = StarWhite,
    onSurface = StarWhite
)

private val LightColorScheme = lightColorScheme(
    primary = SpaceBlue,
    secondary = NebulaPurple,
    tertiary = SpaceBlue,
    background = Color(0xFFF5F5F5),  // Fondo claro
    surface = Color(0xFFFFFFFF),     // Superficie clara
    onPrimary = StarWhite,
    onSecondary = StarWhite,
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000)
)

@Composable
fun Sidera_APPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,  // Asegúrate de tener esto definido
        content = content
    )
}