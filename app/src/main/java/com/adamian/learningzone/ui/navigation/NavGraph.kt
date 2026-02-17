package com.adamian.learningzone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.adamian.learningzone.ui.chapterscreen.ChapterScreen
import com.adamian.learningzone.ui.homescreen.HomeScreen
import com.adamian.learningzone.ui.loginscreen.LoginView
import com.adamian.learningzone.ui.quizscreen.QuizScreen
import com.adamian.learningzone.ui.quizscreen.QuizScreenViewModel
import com.adamian.learningzone.ui.quizscreen.SummaryScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.path
    ){
//        addLoginScreen(navController,this)
        addHomeScreen(navController,this)
        addChapterScreen(navController,this)
        addQuizScreen(navController,this)
        addSummaryScreen(navController, this)
    }
}

private fun addLoginScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Login.path) {
        LoginView(
            navigateToHome = { _ ->
                navController.navigate(NavRoute.Home.path)
            }
        )
    }
}

private fun addHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Home.path) {
        HomeScreen(
            navigateToChapters = { _ ->
                navController.navigate(NavRoute.Chapters.path)
            }
        )
    }
}

private fun addChapterScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Chapters.path) {
        ChapterScreen(navController = navController)
    }
}

private fun addQuizScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Quiz.path,
        arguments = listOf(
            navArgument("chapterId") { type = NavType.IntType },
            navArgument("isRecap") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val chapterId = backStackEntry.arguments?.getInt("chapterId") ?: 0
        val isRecap = backStackEntry.arguments?.getInt("isRecap") ?: 0

        QuizScreen(
            chapterId = chapterId,
            navController = navController,
            isRecap = isRecap
        )
    }
}

fun addSummaryScreen(
    navController: NavController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Summary.path) { summaryEntry ->
        val quizEntry = remember(summaryEntry) {
            navController.getBackStackEntry(NavRoute.Quiz.path)
        }
        val quizViewModel: QuizScreenViewModel = hiltViewModel(quizEntry)

        SummaryScreen(
            quizViewModel = quizViewModel,
            onNavigateToChapters = {
                navController.popBackStack(
                    route = NavRoute.Chapters.path,
                    inclusive = false
                )
            }
        )
    }
}
