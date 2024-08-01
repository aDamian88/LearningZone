package com.adamian.learningzone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.ui.navigation.NavGraph
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme
import com.adamian.learningzone.ui.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val questionViewModel: QuestionViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // save questions
        setContent {
            LearningZoneAppTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
