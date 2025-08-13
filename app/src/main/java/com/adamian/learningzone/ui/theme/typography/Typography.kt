package com.adamian.learningzone.ui.theme.typography

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.adamian.learningzone.R

val NotoSans = FontFamily(
    Font(R.font.notosansregular, FontWeight.Normal)
)

data class AppTypography(
    val titleLarge: TextStyle,
    val titleNormal: TextStyle,
    val body: TextStyle,
    val bodyBold: TextStyle,
    val labelLarge: TextStyle,
    val labelNormal: TextStyle,
    val labelSmall: TextStyle
)

val localAppTypography = staticCompositionLocalOf {
    AppTypography(
        titleLarge = TextStyle(
            fontFamily = NotoSans,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        titleNormal = TextStyle(
            fontFamily = NotoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp
        ),
        body = TextStyle(
            fontFamily = NotoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        bodyBold = TextStyle(
            fontFamily = NotoSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        labelLarge = TextStyle(
            fontFamily = NotoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        ),
        labelNormal = TextStyle(
            fontFamily = NotoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        labelSmall = TextStyle(
            fontFamily = NotoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        )
    )
}
