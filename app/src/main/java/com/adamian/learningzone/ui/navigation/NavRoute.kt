package com.adamian.learningzone.ui.navigation

sealed class NavRoute(val path: String) {

    object Home: NavRoute("home")
    object Login: NavRoute("login")
    object Chapters: NavRoute("chapters")
    object Quiz: NavRoute("quiz/{chapterId}/{isRecap}")

    companion object {
        fun quizRoute(
            chapterId: Int,
            isRecap: Int
        ) = "quiz/$chapterId/$isRecap"
    }

    object Summary : NavRoute("summary/{correct}/{wrong}") {
        fun createRoute(correct: Int, wrong: Int) = "summary/$correct/$wrong"
    }

}