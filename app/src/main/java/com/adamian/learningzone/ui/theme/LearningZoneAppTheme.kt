package com.adamian.learningzone.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme.shape
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme.typography
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme.size
import com.adamian.learningzone.ui.theme.color.AppColorScheme
import com.adamian.learningzone.ui.theme.color.darkColorScheme
import com.adamian.learningzone.ui.theme.color.lightColorScheme
import com.adamian.learningzone.ui.theme.color.localAppColorScheme
import com.adamian.learningzone.ui.theme.shape.AppShape
import com.adamian.learningzone.ui.theme.shape.localAppShape
import com.adamian.learningzone.ui.theme.size.AppSize
import com.adamian.learningzone.ui.theme.size.localAppSize
import com.adamian.learningzone.ui.theme.typography.AppTypography
import com.adamian.learningzone.ui.theme.typography.localAppTypography


@Composable
fun LearningZoneAppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme
    CompositionLocalProvider(
        localAppColorScheme provides colorScheme,
        localAppTypography provides typography,
        localAppShape provides shape,
        localAppSize provides size,
        content = content
    )
}

object LearningZoneAppTheme {
    val colorScheme: AppColorScheme
        @Composable get() = localAppColorScheme.current

    val typography: AppTypography
        @Composable get() = localAppTypography.current

    val shape: AppShape
        @Composable get() = localAppShape.current

    val size: AppSize
        @Composable get() = localAppSize.current
}
