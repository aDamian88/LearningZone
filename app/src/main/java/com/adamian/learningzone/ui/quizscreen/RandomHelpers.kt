package com.adamian.learningzone.ui.quizscreen

import com.adamian.learningzone.R

private val doodleIcons = listOf(
    R.drawable.shoedoodle,
    R.drawable.papareairdoodle,
    R.drawable.notedoodle,
    R.drawable.bagdoodle,
    R.drawable.headphonesdoodle,
    R.drawable.girlbagdoodle,
)

private val lotties = listOf(
    R.raw.underline,
    R.raw.elegantunderline,
    R.raw.slowunderline,
    R.raw.underlines,
    R.raw.wavesunderline,
    R.raw.waveunderline
)

fun randomDoodleIcon(exclude: Int? = null): Int {
    val choices = exclude?.let { doodleIcons.filter { it != exclude } } ?: doodleIcons
    return choices.random()
}

fun randomLottie(): Int = lotties.random()
