package com.adamian.learningzone.ui.theme.size

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppSize(
    val large: Dp = 24.dp,
    val medium: Dp = 16.dp,
    val small: Dp = 14.dp
)

val localAppSize = staticCompositionLocalOf {
    AppSize(
        large = Dp.Unspecified,
        medium = Dp.Unspecified,
        small = Dp.Unspecified
    )
}