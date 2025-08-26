package com.adamian.learningzone.ui.theme.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColorScheme(
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val topSurface: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val quaternary: Color,
    val onQuaternary: Color
)

val darkColorScheme = AppColorScheme(
    background = gray100Dark,
    onBackground = white,
    surface = gray800Dark,
    topSurface = gray600Dark,
    primary = purple200Dark,
    onPrimary = purple100Dark,
    secondary = red200Dark,
    onSecondary = red100Dark,
    tertiary = yellow200Dark,
    onTertiary = yellow100Dark,
    quaternary = teal200Dark,
    onQuaternary = teal100Dark
)

val lightColorScheme = AppColorScheme(
    background = gray100,
    onBackground = black,
    surface = gray100,
    topSurface = gray300,
    primary = purple200,
    onPrimary = purple100,
    secondary = red200,
    onSecondary = red100,
    tertiary = yellow200,
    onTertiary = yellow100,
    quaternary = teal200,
    onQuaternary = teal100
)

val localAppColorScheme = staticCompositionLocalOf {
    lightColorScheme
}
