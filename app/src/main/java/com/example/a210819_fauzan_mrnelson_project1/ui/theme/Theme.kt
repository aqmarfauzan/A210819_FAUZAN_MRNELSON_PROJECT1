package com.example.a210819_fauzan_mrnelson_project1.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = TextWhite,

    secondary = LightGreen,
    tertiary = AccentYellow,

    background = BackgroundLight,
    surface = SurfaceLight,

    onBackground = TextPrimary,
    onSurface = TextPrimary,

    error = ErrorRed
)

val AppShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

@Composable
fun SolarTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}