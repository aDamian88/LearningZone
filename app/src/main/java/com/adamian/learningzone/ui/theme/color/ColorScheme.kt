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
    val onQuaternary: Color,
    val error: Color,
    val onError: Color,
    val success: Color,
    val onSuccess: Color,
    val warning: Color,
    val onWarning: Color,
    val info: Color,
    val onInfo: Color
)

val lightColorScheme = AppColorScheme(
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    topSurface = topSurfaceLight,
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    quaternary = quaternaryLight,
    onQuaternary = onQuaternaryLight,
    error = errorLight,
    onError = onErrorLight,
    success = successLight,
    onSuccess = onSuccessLight,
    warning = warningLight,
    onWarning = onWarningLight,
    info = infoLight,
    onInfo = onInfoLight
)

val darkColorScheme = AppColorScheme(
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    topSurface = topSurfaceDark,
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    quaternary = quaternaryDark,
    onQuaternary = onQuaternaryDark,
    error = errorDark,
    onError = onErrorDark,
    success = successDark,
    onSuccess = onSuccessDark,
    warning = warningDark,
    onWarning = onWarningDark,
    info = infoDark,
    onInfo = onInfoDark
)


val localAppColorScheme = staticCompositionLocalOf {
    lightColorScheme
}
