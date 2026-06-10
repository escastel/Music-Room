package com.example.musicroom.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp
)

data class Spacing(
    val default: Dp = 0.dp,
    val xxsmall: Dp = 2.dp,
    val xsmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val xlarge: Dp = 32.dp,
    val xxlarge: Dp = 40.dp,
    val huge: Dp = 48.dp
)

val LocalAppDimensions = compositionLocalOf { Dimensions() }

val LocalSpacing = staticCompositionLocalOf { Spacing() }
