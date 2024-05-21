package com.adamian.learningzone.ui.theme.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColorScheme(
    val background: Color,
    val onBackground: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color
)

val darkColorScheme = AppColorScheme(
    background = gray800_2,
    onBackground = gray100_2,
    primary = purple700,
    onPrimary = purple900,
    secondary = teal700,
    onSecondary = teal900
)

val lightColorScheme = AppColorScheme(
    background = gray100,
    onBackground = gray800,
    primary = purple200,
    onPrimary = purple100,
    secondary = red200,
    onSecondary = red100
)

val localAppColorScheme = staticCompositionLocalOf {
    lightColorScheme // Default to light color scheme
}