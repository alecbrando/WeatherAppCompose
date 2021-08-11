package com.alecbrando.composeweatherapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = FontColorDark,
    primaryVariant = Purple700,
    onSurface = ButtonColorDark,
    secondary = NavButtonColorDark,
    background = Gray100,
)

private val LightColorPalette = lightColors(
    primary = FontColorLight,
    primaryVariant = Purple700,
    secondary = NavButtonColorLight,
    onSurface = ButtonColorLight,
    background = White100

    /* Other default colors to override
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComposeWeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}