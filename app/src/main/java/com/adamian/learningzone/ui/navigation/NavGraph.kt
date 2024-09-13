package com.adamian.learningzone.ui.navigation

import androidx.compose.runtime.Composable
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
        arguments = listOf(navArgument("chapterId") { type = NavType.IntType }) // Define argument type
    ) { backStackEntry ->
        val chapterId = backStackEntry.arguments?.getInt("chapterId") ?: 0 // Retrieve the chapterId argument
        QuizScreen(
            chapterId = chapterId,
            navController = navController
        )
    }
}
