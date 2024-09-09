package com.adamian.learningzone.ui.navigation

sealed class NavRoute(val path: String) {

    object Home: NavRoute("home")
    object Login: NavRoute("login")
    object Chapters: NavRoute("chapters")
    object Quiz: NavRoute("quiz/{chapterId}")

    companion object {
        fun quizRoute(chapterId: Int) = "quiz/$chapterId" // Helper function to create route with argument
    }
}